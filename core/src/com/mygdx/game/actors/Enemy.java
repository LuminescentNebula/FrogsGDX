package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.interfaces.Collidable;
import com.mygdx.game.pools.MainPool;
import com.mygdx.game.MoveAction;
import com.mygdx.game.interfaces.Health;
import com.mygdx.game.interfaces.Movable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

public class Enemy extends Group implements Movable, Collidable,Health {

    Rectangle bounds = new Rectangle();
    Image image;
    private final int ID;

    private int health;
    private int maxHealth=100;
    private boolean targeted=false;

    protected final int maxAction=1000;
    protected float action=0;
    private LinkedList<MoveAction> pathPoints;
    //TODO: Any Angle pathfinding
    //todo: Draw projection when character making multi point moving
    public Enemy(int ID){
        image = new Image(new Texture(Gdx.files.internal("ghost.png")));
        addActor(image);
        this.ID = ID;
    }

    @Override
    public void act(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool) {
        navigate(getCenter(),mainPool.get(Character.class).get(0).getCenter(),
                shapeRenderer,mainPool,mainPool.get(Character.class).get(0).getId());
        //move();
    }

    @Getter
    private class Node {
        Vector2 vector;
        int id;
        boolean ignoreBounds=false;

        public Node(Vector2 vector, int id) {
            this.vector = vector;
            this.id = id;
        }
        public Node(Vector2 vector, int id,boolean ignoreBounds) {
            this.vector = vector;
            this.id = id;
            this.ignoreBounds=ignoreBounds;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node node = (Node) o;
            return id == node.id && Objects.equals(vector, node.vector);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vector, id);
        }
    }

    public class Graph {
        @Getter
        private Set<Node> nodes=new HashSet<>();
        @Getter
        private Map<Node, Set<Node>> edges=new HashMap<>();

        public Graph(Node start) {
            nodes.add(start);
            edges.put(start, new HashSet<>());
        }

        public boolean addNode(Node start,Node end) {
            if (nodes.add(end)) {
                edges.put(end, new HashSet<Node>(){{add(start);}});
            } else {
                edges.get(end).add(start);
            }
            return edges.get(start).add(end);
        }

        boolean findEdge(Node start, Node end){
            return edges.get(start).contains(end);
        }


        @Override
        public String toString() {
            StringBuilder string=new StringBuilder();
            string.append("Graph {\n");
            for (Node vector : nodes) {
                string.append(vector.vector).append("\n");
                for (Node edge : edges.get(vector)) {
                    string.append("\t|").append(edge.vector).append("\n");
                }
                string.append("\n");
            }
            string.append("}");
            return string.toString();
        }
    }

    String graph="";

    public buildGraph(Vector2 start,Vector2 end,ShapeRenderer shapeRenderer, MainPool mainPool,int target){

    }

    public void navigate(Vector2 start,Vector2 end,ShapeRenderer shapeRenderer, MainPool mainPool,int target) {
        ArrayList<Collidable> collidables = mainPool.get(Collidable.class);
        collidables.sort(Comparator.comparing(health -> health.getCenter().dst(start)));
        Graph path=new Graph(new Node(start,getId(),true));
        //System.out.println(start+" -> "+end);
        recurse(path,new Node(start,getId(),true),new Node(end,target,true),collidables,shapeRenderer);

        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        if (!Objects.equals(path.toString(), graph)){
            graph=path.toString();
            System.out.println(graph);
        }

        for (Node vector:path.getNodes()){
            shapeRenderer.circle(vector.vector.x, vector.vector.y, 5);
            for (Node edge:path.getEdges().get(vector)){
                shapeRenderer.rectLine(vector.vector,edge.vector,2);
            }
        }
    }

    public void recurse(Graph path, Node start, Node end, ArrayList<Collidable> collidables, ShapeRenderer shapeRenderer) {
        boolean nothing=true;
        for (Collidable collidable : collidables) { //Проверяем, что между началом и концом нет препятствий
            if (collidable.getId() != start.getId() && //Проверяемый collidable не должен быть объектом начала или конца
                    collidable.getId() != end.id  &&
                    Intersector.intersectSegmentRectangle(start.vector, end.vector, collidable.getBounds())) {
                nothing=false;
                extracted(path, start, end, collidables, shapeRenderer, collidable);
            }
        }
        if (nothing){
            if (!start.ignoreBounds) {
                if (!Intersector.intersectSegmentRectangle(start.vector, end.vector, shrink(collidables.stream().filter(
                        collidable -> collidable.getId() == start.id).findFirst().get().getBounds(), 1))) {
                    path.addNode(start, end);
                }
            } else {
                path.addNode(start, end);
            }
        }
    }

    private void extracted(Graph path, Node start, Node end, ArrayList<Collidable> collidables, ShapeRenderer shapeRenderer, Collidable current) {
        List<Vector2> points = points(current.getBounds());
        boolean atLeastOne=false;
        for (int i = 0; i < points.size(); i++) { //Проверяем каждую точку объекта, с которым столкнулись
            boolean collided=false;
            if (!Intersector.intersectSegmentRectangle(start.vector, points.get(i), shrink(current.getBounds(), 1))) { // Есди от начала до точки не пересекается этот самый объект

                for (Collidable collidable : collidables) { //Проверяем, что между точкой и началом нет объектов
                    if (collidable.getId() != start.getId() && //Проверяем что объект не начало, не конец и не тот объект, чьи точки проверяем.
                            collidable.getId() != end.id &&
                            current.getId() != collidable.getId()) {
                        if (Intersector.intersectSegmentRectangle(start.vector, points.get(i), collidable.getBounds())) {
                            collided = true;
                            extracted(path, start, end, collidables, shapeRenderer, collidable);
                            System.out.println("collided " + i + " " + current.getId() + " " + collidable.getId());
                            break;
                        }
                    }
                }

                if (!collided) {
                    Node node = new Node(points.get(i), current.getId());
                    path.addNode(start, node);
                    recurse(path, node, end, collidables, shapeRenderer);
                    if (!atLeastOne) {
                        recurse(path, node, end, collidables, shapeRenderer);
                        Node sideNode1 = new Node(points.get(i + 1 == 4 ? 0 : i + 1), current.getId());
                        path.addNode(node, sideNode1);
                        recurse(path, sideNode1, end, collidables, shapeRenderer);

                        Node sideNode2 = new Node(points.get((i - 1 == -1 ? 3 : i - 1)), current.getId());
                        path.addNode(node, sideNode2);
                        recurse(path, sideNode2, end, collidables, shapeRenderer);

                        Node sideNode3 = new Node(points.get((i + 2) % 4), current.getId());
                        path.addNode(sideNode1, sideNode3);
                        path.addNode(sideNode2, sideNode3);
                        recurse(path, sideNode3, end, collidables, shapeRenderer);
                    }
                    atLeastOne=true;
                }
            }
        }
    }

    private Rectangle shrink(Rectangle rectangle, int n){
        return new Rectangle(rectangle.x+n,
                rectangle.y+n,
                rectangle.width-n,
                rectangle.height-n);
    }

    private List<Vector2> points(Rectangle rectangle) {
        return new ArrayList<Vector2>() {{
            add(new Vector2(rectangle.x, rectangle.y));
            add(new Vector2(rectangle.x + rectangle.width, rectangle.y));
            add(new Vector2(rectangle.x + rectangle.width, rectangle.y + rectangle.height));
            add(new Vector2(rectangle.x, rectangle.y + rectangle.height));
        }};
    }

    public void move(Vector2 cursor, Batch batch, ShapeRenderer shapeRenderer, MainPool mainPool){
        //Projection.calculateProjection();
    }

    @Override
    public void setSize(float width,float height){
        image.setSize(width,height);
    }

    @Override
    public void setPosition(float x, float y) {
        bounds.set(x, y, getWidth(), getHeight());
        super.setPosition(x, y);
    }

    @Override
    public float getWidth() {
        return image.getWidth();
    }

    @Override
    public float getHeight() {
        return image.getHeight();
    }

    @Override
    public float getCenterX(){
        return getX()+getWidth()/2;
    }

    @Override
    public float getCenterY(){
        return getY()+getHeight()/2;
    }

    @Override
    public Vector2 getCenter(){
        return new Vector2(getCenterX(), getCenterY());
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public Boolean isCollidable() {
        return true;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void setHealth(int health) {
        this.health=health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void dealHealth(int health) {
        this.health-=health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth=maxHealth;
    }

    @Override
    public boolean setTargeted(boolean targeted) {
        this.targeted=targeted;
        return targeted;
    }

    @Override
    public float getMaxAction() {
        return maxAction;
    }

    @Override
    public float getAvailableAction() {
        //TODO
        return 0;
    }

    @Override
    public float getAction() {
        return action;
    }

    @Override
    public void addAction(float action) {
        this.action+=action;
    }

    @Override
    public LinkedList<MoveAction> getPathPoints() {
        return pathPoints;
    }
}
