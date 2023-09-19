Жанр: стратегия, rougelike  
Сеттинг: фэнтези  
Графика: векторная, изометрия, вид сверху  
Технологии: LibGDX  
Платформы: Web, PC  
Референсы: Into The Breach,

Особенности:
- перемещение не ограниченное клетками(тайлами)
- процедурно генерируемые локации
-

Лор:
Королевство лягушек ввергнуто в хаос злобными криптидами. Король и королева похищены. Принцесса собирает отряд элитных войнов, чтобы спасти родителей и спасти королевство.  
Королевство разделено на несколько регионов, в каждом из которых вторжением управляет один из криптидов.

Механики:

- время:  
  + [ ] у каждого персонажа в ход есть время  
  + [X] время затрачивается при перемещении  
  + [ ] время затрачивается при атаке  
  + [ ] если времени осталось меньше, чем требует атака, то атака всё равно выполняется  


- перемещение:  
  + [X] при нажатии на персонажа он выделяется  
  + [X] выделенного персонажа можно передвинуть по прямой линии  
  + [X] с зажатым shift можно передвигаться по ломанной линии  
  + [X] правая кнопка отменяет последнее перемещение по сломанной линии или отменяет выделение, если перемещения не было  
  + [X] от изначального местоположения персонажа проводится линия, на конце линии отрисовывается полупрозрачный персонаж  
  + [ ] коллизия с объектами на карте
  + [X] на изломах сломанной линии рисуются кружочки  


- атака:  
  + [ ] внизу экрана будут кнопки атак, доступных выделенному персонажу  
  + [ ] атаки имею разную дальность и область применения  
  + [ ] выстрел - точечный снаряд выстрелмвающий по прямой линии  
  + [ ] конус - атака затрагивающая всех, кто находится перед персонажем в конусе  
  + [ ] круг - атака затрагивающая всех, кто находится внутри круга  
  + [ ] цепь - атака затрагивает по цепочку всех противников, которые находятся на определённом расстоянии от изначальной цели атаки и так далее по цепочке  


- прогрессия

|               |     **Дальний**     |         **Магия**         |      **Ближний**       |
|:-------------:|:-------------------:|:-------------------------:|:----------------------:|
|   **Атака**   |  Лучник<br/>(Лук)   | Заклинатель<br/>(Палочка) |     Воин<br/>(Меч)     |
| **Поддержка** |        Бард         |  Жрец<br/>(символ/книга)  |         Лекарь         |
|  **Защита**   | Паладин<br/>(Копье) |         *Иллюзор*         | Щитоносец<br/>(2 щита) |


//Нужно сделать пул, со всеми объектами, которые имеют коллизию на карте  
При отрисовки проекции должно проверяться
- Полупрозрачный не пересакается ни с чем
- От последней точки до полупрозрачного нет ничего

Ели что-то false, то отрисовываются на свободном месте рядом, то есть на точке пересечения-width-height (?)