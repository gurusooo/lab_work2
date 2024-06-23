# lab_work2
### Работу выполнила Шкулева М.А, 22ПИ-1
### Логин, использованный в контестах: mashkuleva@edu.hse.ru
### О работе:
Даны прямоугольники на плоскости с углами в целочисленных координатах ([1..10^9],[1..10^9]).

Требуется как можно быстрее выдавать ответ на вопрос «Скольким прямоугольникам принадлежит точка (x,y)?» Подготовка данных должна занимать мало времени.
Включены только нижние границы => (x1<= x) && (x<x2) && (y1<=y) && (y<y2).

**Пример**

Прямоугольники: {(2,2),(6,8)}, {(5,4),(9,10)}, {(4,0),(11,6)}, {(8,2),(12,12)}

Точка-ответ: 

(2,2) -> 1

(12,12) -> 0

(10,4) -> 2

(5,5) -> 3

(2,10) -> 0

(2,8) -> 0

### Цели лабораторной работы:
* Реализовать три разных решения задачи
* Выяснить при каком объеме начальных данных и точек какой алгоритм эффективнее.

**Алгоритм перебора**
* Без подготовки. При поиске – просто перебор всех прямоугольников
* Подготовка O(1), поиск O(N)
  
**Алгоритм на карте**

* Построение карты.
* Подготовка O(N^3), поиск O(logN)
  
**Алгоритм на дереве**
  
* Сжатие координат и построение персистентного дерева отрезков 
* Подготовка O(NlogN), поиск O(logN)

### Тестовые наборы данных 

* Для тестового набора прямоугольников, рекомендуется использовать набор вложенных друг-в-друга с координатами с шагом больше 1, например {(10*i, 10*i), (10*(2N-i), 10*(2N-i))}
* Для тестового набора точек, рекомендуется использовать неслучайный набор распределенных более-менее равномерно по ненулевому пересечению прямоугольников, например хэш функции от i с разным базисом для x и y.   (p*i)^31%(20*N), p-большое простое, разное для x и y.

### Устройство гита

* contest_codes - коды для прохождения контестов;

* lab_codes - коды для подсчёта времени подготовки и выполнения запросов

* raw_data - таблицы с данными и скриншоты
  
### Вывод:

<img width="800" alt="excel_data.png" src="https://github.com/gurusooo/lab_work2/blob/main/raw_data/excel_data.png">

* Генерация данных при подготовке алгоритма на карте занимает крайне много времени, как я предполагаю из-за обилия вложенных циклов. Код можно и нужно оптимизировать, хотя по моей гипотезе (а так же учитывая сложности алгоритмов подготовки), подготовка для "карточного" алгоритма всё равно не будет самой быстрой из трёх;
* Говоря о времени выполнения запросов, не хватает данных о работе алгоритма на карте с большими запросами, но он однозначно выигрывает у наивной реализации, когда речь идёт о малых данных;
* В моей реализации сегментное дерево отрезков сильно уходит в рекурсию, из-за чего на больших данных происходит переполнение стека. Тем не менее, можно сказать, что подготовка данных на третьем алгоритме среди остальных самая большая, однако с лихвой обходит предыдущие по времени подсчёта точек. Но с памятью у третьего определённо проблемы.
* Учить новый язык в процессе написания лабораторной - не очень хорошая идея)
