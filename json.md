```json lines
{
    "weapons": [
        {
            "id": "1",      //Unique id, should be autoincremented
            "draw-act":[    //Может быть несколько отрисовщиков и каждому отрисовщику соответствует проверка
              {
                "radius":10,
                "minLength":10,
                "maxLength":50,
                "damage":10
                "draw": 1, //id 
                "act": 1,  //id
              }
            ],
            "flags": [
              false, //checkNotMaster = 1
              false, //stopOnFirstCollision   = 2
              false, //chainDamage = 4
              false  //chainDamageIncrease = 8
            ], 

        }
    ]
}
```
Имя и описание подтягивается из документа со строками в соответствии с **id**

Что не работает друг с другом:

|                          | **Снаряд** | **Выстрел** | **Сквозной<br>выстрел** | **Цель** | **Конус** | **Радиус** |
|:------------------------:|:----------:|:-----------:|:-----------------------:|:--------:|:---------:|:-----------|
|    **checkNotMaster**    |            |             |                         |          |           |            |
| **stopOnFirstCollision** |            |             |                         |          |           |            |
|     **chainDamage**      |            |             |                         |          |           |            |
| **chainDamageIncrease**  |            |             |                         |          |           |            |
|        **radius**        |            |      X      |            X            |          |           |            |
|      **minLength**       |            |             |            X            |          |           |            |
|      **maxLength**       |            |             |            X            |          |           |            |
