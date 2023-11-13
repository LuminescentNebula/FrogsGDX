package com.mygdx.game;

public class GraphBuilder {

//
//
//    @Getter
//    private class Node {
//        Vector2 vector;
//        int id;
//        boolean ignoreBounds=false;
//
//        public Node(Vector2 vector, int id) {
//            this.vector = vector;
//            this.id = id;
//        }
//        public Node(Vector2 vector, int id,boolean ignoreBounds) {
//            this.vector = vector;
//            this.id = id;
//            this.ignoreBounds=ignoreBounds;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (!(o instanceof Node)) return false;
//            Node node = (Node) o;
//            return id == node.id && Objects.equals(vector, node.vector);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(vector, id);
//        }
//    }
//
//    public class Graph {
//        @Getter
//        private Set<Node> nodes=new HashSet<>();
//        @Getter
//        private Map<Node, Set<Node>> edges=new HashMap<>();
//
//        public Graph(Node start) {
//            nodes.add(start);
//            edges.put(start, new HashSet<>());
//        }
//
//        public void addNode(Node node) {
//            if (nodes.add(node)) {
//                edges.put(node, new HashSet<>());
//            }
//        }
//
//        public boolean addNode(Node start,Node end) {
//            if (nodes.add(end)) {
//                edges.put(end, new HashSet<Node>(){{add(start);}});
//            } else {
//                edges.get(end).add(start);
//            }
//            return edges.get(start).add(end);
//        }
//
//        boolean findEdge(Node start, Node end){
//            return edges.get(start).contains(end);
//        }
//
//        boolean isConnected(Node node1,Node node2){
//            Set<Node> set=edges.get(node1);
//            if (set!=null) {
//                return set.contains(node2);
//            }
//            return false;
//        }
//
//        @Override
//        public String toString() {
//            StringBuilder string=new StringBuilder();
//            string.append("Graph {\n");
//            for (Node vector : nodes) {
//                string.append(vector.vector).append("\n");
//                for (Node edge : edges.get(vector)) {
//                    string.append("\t|").append(edge.vector).append("\n");
//                }
//                string.append("\n");
//            }
//            string.append("}");
//            return string.toString();
//        }
//    }
//
//    String graph="";
//
//    public void buildGraph(Enemy.Graph path, Vector2 start, Vector2 end, ShapeRenderer shapeRenderer, ArrayList<Collidable> collidables, int targetID) {
//        path.addNode(new Enemy.Node(end, targetID,false));
//        for (Collidable collidable : collidables) {
//            if (collidable.getId() != getId() && collidable.getId() != targetID) {
//                List<Vector2> points = points(collidable.getBounds());
//                Enemy.Node sideNode1 = new Enemy.Node(points.get(0), collidable.getId());
//                Enemy.Node sideNode2 = new Enemy.Node(points.get(1), collidable.getId());
//                Enemy.Node sideNode3 = new Enemy.Node(points.get(2), collidable.getId());
//                Enemy.Node sideNode4 = new Enemy.Node(points.get(3), collidable.getId());
//                path.addNode(sideNode1);
//                path.addNode(sideNode1, sideNode2);
//                path.addNode(sideNode2, sideNode3);
//                path.addNode(sideNode3, sideNode4);
//                path.addNode(sideNode4, sideNode1);
//            }
//        }
//    }
//    public void connect(Enemy.Graph path, ArrayList<Collidable> collidables){
//        Enemy.Node[] nodes=path.getNodes().toArray(new Enemy.Node[0]);
//        nodes= Arrays.stream(nodes).sorted(Comparator.comparing(Enemy.Node::getId)).toArray(Enemy.Node[]::new);
//        for (int i=0;i<path.getNodes().size()-1;i++) {
//            for (int j = i + 1; j < path.getNodes().size(); j++) {
//                if (nodes[i].id!=nodes[j].id) {
//                    boolean collided = false;
//                    for (Collidable collidable : collidables) {
//                        if (collidable.isCollidable() && collidable.isAvoidable()) {
//                            Rectangle bound = collidable.getBounds();
//                            if (collidable.getId() == nodes[i].id || collidable.getId() == nodes[j].id) {
//                                bound = shrink(bound, 1);
//                            }
//
//                            if (Intersector.intersectSegmentRectangle(nodes[i].vector, nodes[j].vector, bound)) {
//                                collided = true;
//                                break;
//                            }
//                        }
//                    }
//                    if (!collided) {
//                        path.addNode(nodes[i], nodes[j]);
//                    }
//                }
//            }
//        }
//    }
//
//    public void navigate(Vector2 start, Vector2 end, ShapeRenderer shapeRenderer, MainPool mainPool, int target) {
//        ArrayList<Collidable> collidables = mainPool.get(Collidable.class);
//        collidables.sort(Comparator.comparing(health -> health.getCenter().dst(start)));
//        Enemy.Graph path=new Enemy.Graph(new Enemy.Node(start,getId(),true));
//        //System.out.println(start+" -> "+end);
//        //recurse(path,new Node(start,getId(),true),new Node(end,target,true),collidables,shapeRenderer);
//        buildGraph(path,start,end,shapeRenderer,collidables,target);
//        connect(path,collidables);
//        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.BLUE);
//        if (!Objects.equals(path.toString(), graph)){
//            graph=path.toString();
//            System.out.println(graph);
//        }
//
//        for (Enemy.Node vector:path.getNodes()){
//            shapeRenderer.circle(vector.vector.x, vector.vector.y, 5);
//            for (Enemy.Node edge:path.getEdges().get(vector)){
//                shapeRenderer.rectLine(vector.vector,edge.vector,2);
//            }
//        }
//    }
//
//    public void recurse(Enemy.Graph path, Enemy.Node start, Enemy.Node end, ArrayList<Collidable> collidables, ShapeRenderer shapeRenderer) {
//        boolean nothing=true;
//        for (Collidable collidable : collidables) { //Проверяем, что между началом и концом нет препятствий
//            if (collidable.getId() != start.getId() && //Проверяемый collidable не должен быть объектом начала или конца
//                    collidable.getId() != end.id  &&
//                    Intersector.intersectSegmentRectangle(start.vector, end.vector, collidable.getBounds())) {
//                nothing=false;
//                extracted(path, start, end, collidables, shapeRenderer, collidable);
//            }
//        }
//        if (nothing){
//            if (!start.ignoreBounds) {
//                if (!Intersector.intersectSegmentRectangle(start.vector, end.vector, shrink(collidables.stream().filter(
//                        collidable -> collidable.getId() == start.id).findFirst().get().getBounds(), 1))) {
//                    path.addNode(start, end);
//                }
//            } else {
//                path.addNode(start, end);
//            }
//        }
//    }
//
//    private void extracted(Enemy.Graph path, Enemy.Node start, Enemy.Node end, ArrayList<Collidable> collidables, ShapeRenderer shapeRenderer, Collidable current) {
//        List<Vector2> points = points(current.getBounds());
//        boolean atLeastOne=false;
//        for (int i = 0; i < points.size(); i++) { //Проверяем каждую точку объекта, с которым столкнулись
//            boolean collided=false;
//            if (!Intersector.intersectSegmentRectangle(start.vector, points.get(i), shrink(current.getBounds(), 1))) { // Есди от начала до точки не пересекается этот самый объект
//
//                for (Collidable collidable : collidables) { //Проверяем, что между точкой и началом нет объектов
//                    if (collidable.getId() != start.getId() && //Проверяем что объект не начало, не конец и не тот объект, чьи точки проверяем.
//                            collidable.getId() != end.id &&
//                            current.getId() != collidable.getId()) {
//                        if (Intersector.intersectSegmentRectangle(start.vector, points.get(i), collidable.getBounds())) {
//                            collided = true;
//                            extracted(path, start, end, collidables, shapeRenderer, collidable);
//                            System.out.println("collided " + i + " " + current.getId() + " " + collidable.getId());
//                            break;
//                        }
//                    }
//                }
//
//                if (!collided) {
//                    Enemy.Node node = new Enemy.Node(points.get(i), current.getId());
//                    path.addNode(start, node);
//                    recurse(path, node, end, collidables, shapeRenderer);
//                    if (!atLeastOne) {
//                        recurse(path, node, end, collidables, shapeRenderer);
//                        Enemy.Node sideNode1 = new Enemy.Node(points.get(i + 1 == 4 ? 0 : i + 1), current.getId());
//                        path.addNode(node, sideNode1);
//                        recurse(path, sideNode1, end, collidables, shapeRenderer);
//
//                        Enemy.Node sideNode2 = new Enemy.Node(points.get((i - 1 == -1 ? 3 : i - 1)), current.getId());
//                        path.addNode(node, sideNode2);
//                        recurse(path, sideNode2, end, collidables, shapeRenderer);
//
//                        Enemy.Node sideNode3 = new Enemy.Node(points.get((i + 2) % 4), current.getId());
//                        path.addNode(sideNode1, sideNode3);
//                        path.addNode(sideNode2, sideNode3);
//                        recurse(path, sideNode3, end, collidables, shapeRenderer);
//                    }
//                    atLeastOne=true;
//                }
//            }
//        }
//    }
//
//    private Rectangle shrink(Rectangle rectangle, int n){
//        return new Rectangle(rectangle.x+n,
//                rectangle.y+n,
//                rectangle.width-n,
//                rectangle.height-n);
//    }
//
//    private List<Vector2> points(Rectangle rectangle) {
//        return new ArrayList<Vector2>() {{
//            add(new Vector2(rectangle.x, rectangle.y));
//            add(new Vector2(rectangle.x + rectangle.width, rectangle.y));
//            add(new Vector2(rectangle.x + rectangle.width, rectangle.y + rectangle.height));
//            add(new Vector2(rectangle.x, rectangle.y + rectangle.height));
//        }};
//    }

}
