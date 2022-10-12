import numpy as np
import pandas as pd
from sklearn.datasets import fetch_california_housing


def print_work_2():
    print('-----------------------------------------------1start')
    exercise_1()
    print('-----------------------------------------------1end ->2start')
    # exercise_2(-1, True)
    print('-----------------------------------------------2end ->3start')
    # exercise_3(10, 10)
    print('-----------------------------------------------3end ->4start')
    # exercise_4()
    print('-----------------------------------------------4end ->5start')
    # exercise_5()
    print('-----------------------------------------------5end')
    pass


def exercise_1():
    str_sum_number = 0
    list_square = []
    while True:
        try:
            enter_number = int(input())
            list_square.append(enter_number ** 2)
            str_sum_number += enter_number
            if str_sum_number == 0: break
        except ValueError:
            print('Вы ввели не число!')

    print(list_square)


def exercise_2(n, enable=False):
    if not enable or n < 0:
        print('exercise_1=error')
        return
    list_number = []
    i = 1
    while i != n + 1:
        print_sequence(i, list_number)
        i += 1
    print(list_number)


def print_sequence(number_print, list_number):
    i = 0
    while i != number_print:
        i += 1
        list_number.append(number_print)


def exercise_3(x, y):
    list_number = []
    matrix = np.random.randint(10, size=(x, y))
    for x in matrix:
        for y in x:
            list_number.append(y)
    print(matrix)
    print(list_number)
    pass


def exercise_4():
    a = [1, 2, 3, 4, 2, 1, 3, 4, 5, 6, 5, 4, 3, 2]
    b = ['a', 'b', 'c', 'c', 'c', 'b', 'a', 'c', 'a', 'a', 'b', 'c', 'b', 'a']
    dictionary_sum = {}
    for index, element in enumerate(b):
        if len(a) < index: continue
        if not element in dictionary_sum:
            dictionary_sum[element] = a[index]
        else:
            dictionary_sum[element] = dictionary_sum[element] + a[index]
    print(dictionary_sum)


def exercise_5():
    # ну и дрянь это все
    # target = строки
    # target_names = столбцы
    data = fetch_california_housing(as_frame=True)
    data_frame_1 = pd.DataFrame(data.target, columns=data.target_names)
    data_new = pd.concat([data['data'], data_frame_1], axis=1)
    # 6
    print('------------------------->6')
    print(data_new)
    print('6<-------------------------')
    # 7
    print('------------------------->7')
    print(data_new.info())
    print('7<-------------------------')
    # 8
    print('------------------------->8')
    print(data_new.isna().sum())
    print('8<-------------------------')
    # 9
    print('------------------------->9')
    data_new_2 = data_new[(data_new.HouseAge > 50) & (data_new.Population > 2500)]
    print(data_new_2.loc[data_new_2.index, ['HouseAge', 'Population']])
    print('9<-------------------------')
    # 10
    print('------------------------->10')
    print(data_new['MedHouseVal'].max())
    print(data_new['MedHouseVal'].min())
    print('10<-------------------------')
    # 11
    print('------------------------->11')
    print(data_new.apply(lambda x: x.mean(), axis=0))
    print('11<-------------------------')
