import random

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from pathlib import Path
import plotly
import plotly.graph_objs as go
import plotly.express as px
import scipy.stats as sts

location = Path(__file__).absolute().parent
file_location = location / 'ECDCCases.csv'
file_location_bmi = location / 'bmi.csv'


def print_work_6():
    data_1 = pd.read_csv(file_location, sep=',')
    print_percentage_ratio(data_1)
    data_1.drop(['Cumulative_number_for_14_days_of_COVID-19_cases_per_100000', 'geoId'], axis=1, inplace=True)
    data_1['countryterritoryCode'].fillna('other', inplace=True)
    data_1['popData2019'].fillna(data_1['popData2019'].mean(), inplace=True)
    print('--------------------------')
    print_percentage_ratio(data_1)
    data_new = data_1[data_1.deaths > 3000]
    print(data_1.describe())
    print('---------Информация о смертях-----------------')
    print(data_new[['countriesAndTerritories', 'day', 'deaths']])
    print(f'---------Количество > 3000 =={len(data_new)}----------------')

    print(f"-----------Размер до удаления данных = {len(data_1)}")
    data_1.drop_duplicates(inplace=True)
    print(f"-----------Размер после удаления данных = {len(data_1)}")

    print_6()


def print_5():
    data_bmi = pd.read_csv(file_location_bmi, sep=',')

    data_northwest = data_bmi[data_bmi.region == 'northwest']
    data_southwest = data_bmi[data_bmi.region == 'southwest']

    samples_count = 100

    samples_northwest = get_sample(samples_count, data_northwest, 'bmi')
    samples_southwest = get_sample(samples_count, data_southwest, 'bmi')

    mean_northwest = np.mean(samples_northwest)
    mean_southwest = np.mean(samples_southwest)

    res1 = sts.shapiro(samples_northwest)
    res2 = sts.shapiro(samples_southwest)
    print(f"Проверка распределений на нормальность(критерий Шапиро-Уилка northwest --> {res1}")
    print(f"Проверка распределений на нормальность(критерий Шапиро-Уилка southwest --> {res2}")

    res_bartlett = sts.bartlett(samples_northwest, samples_southwest)
    print(f"Проверка гомогенности дисперсии(Критерий Бартлетта) -->> {res_bartlett}")

    t_res = sts.ttest_ind(samples_northwest, samples_southwest)

    print(f"t-критерий Стьюдента -->{t_res}")
    test = ' '


def print_6():
    df_cube = pd.DataFrame({
        'N': range(1, 7),
        'Observed': [97, 98, 109, 95, 97, 104],
        'Expected': [100] * 6
    })

    res = sts.chisquare(df_cube['Expected'], df_cube['Observed'])
    print(df_cube)
    print(res)


def print_7():
    data = pd.DataFrame({'Женат': [89, 17, 11, 43, 22, 1],
                         'Гражданский брак': [80, 22, 20, 35, 6, 4],
                         'Не состоит в отношениях': [35, 44, 35, 6, 8, 22]})
    data.index = ['Полный рабочий день', 'Частичная занятость', 'Временно не работает', 'На домохозяйстве', 'На пенсии', 'Учёба']
    print(data)
    print(sts.chi2_contingency(data)[:3])


def print_percentage_ratio(data):
    len_data = len(data)
    for column in data.columns:
        missing = (data[column].isna().sum() / len_data) * 100
        print(f" {column} --> {round(missing, 1)}%")


def get_sample(size_sample, data, column):
    list_sample = []
    for i in range(size_sample):
        list_sample.append(data[column].sample().values[0])
    return list_sample
