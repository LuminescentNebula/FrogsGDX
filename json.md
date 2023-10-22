```json lines
{
    "weapons": [
      {
        "id": "1",
        //Unique id, should be autoincremented
        "draw-act": [
          //Может быть несколько отрисовщиков и каждому отрисовщику соответствует проверка
          {
            "radius": 10,
            "minLength": 10,
            "maxLength": 50,
            "damage": 10,
            "draw": 1, //id 
            "act": 1, //id
            "mods": [
              {
                "type": "rotation",
                "angle": 90
              },
              {
                "type": "translationRotated",
                "x": 50,
                "y": 0
              }
            ]
          }
        ],
        "flags": {
          "checkNotMaster": false,
          "stopOnFirstCollision": false,
          "chainDamege": false,
          "chainDamageIncrease": false
        }
      }
    ]
}
```
Имя и описание подтягивается из документа со строками в соответствии с **id**

Что не работает друг с другом:

|                          |   **Снаряд**    |   **Выстрел**   | **Сквозной<br>выстрел** |    **Цель**     |    **Конус**    |    **Радиус**    |
|:------------------------:|:---------------:|:---------------:|:-----------------------:|:---------------:|:---------------:|:----------------:|
|    **checkNotMaster**    |                 |                 |                         |                 |                 |                  |
| **stopOnFirstCollision** |                 |                 |                         |                 |                 |                  |
|     **chainDamage**      |                 |                 |                         |                 |                 |                  |
| **chainDamageIncrease**  | Not implemented | Not implemented |     Not implemented     | Not implemented | Not implemented | Not implemented  |
|        **radius**        |                 |        X        |            X            |                 |        X        |                  |
|      **minLength**       |                 |                 |            X            |                 |                 |                  |
|      **maxLength**       |                 |                 |            X            |                 |                 |                  |
