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
file_location = location / 'insurance.csv'


def print_work_4():
    data = pd.read_csv(file_location, sep=',')
    fig, ax = plt.subplots(1, 4, figsize=(15, 6))
    # show_4(data)
    show_6(data)
    # print(data.describe())


def show_7(data):
    print('Доверительный интервал равен для 95% ',
          sts.norm.interval(alpha=0.95, loc=np.mean(data), scale=sts.sem(data)))
    print('Доверительный интервал равен для 99% ',
          sts.norm.interval(alpha=0.95, loc=np.mean(data), scale=sts.sem(data)))


def show_6(data):
    samples = []
    number_passes = [50, 50, 500, 500]  # кооличество проходов
    for pasess in number_passes:
        sample_mean = []
        for i in range(pasess):
            sample_mean.append(np.mean(get_sample(30, data)))
        samples.append(sample_mean)

    # for i in range(1000):
    #     sample = []
    #     for j in range(300):
    #         sample.append(date['bmi'].sample().values[0])
    #
    #     sample = np.array(sample)
    #     sample_mean.append(sample.mean())
    #     sample_std.append(sample.std())
    #     samples.append(sample)
    #
    # sample_mean_np = np.array(sample_mean)
    # sample_std_np = np.array(sample_std)
    fig, ax = plt.subplots(1, 4, figsize=(15, 6))

    for index, field in enumerate(samples):
        d = 0.1
        num_bins = int((max(field) - min(field)) // d)
        ax[index].hist(field, bins=num_bins)
        ax[index].set_title(index)
        print(f'Средняя выборка для [{index}] = {np.mean(field)}')
        print(f'Стандартное отклонение  для [{index}] = {np.std(field)}')
        show_7(field)

    plt.show()


def get_sample(size_sample, data):
    list_sample = []
    for i in range(size_sample):
        list_sample.append(data['bmi'].sample().values[0])
    return list_sample


def show_5(data):
    fig, ax = plt.subplots(1, 4, figsize=(15, 6))
    numerics = ['int16', 'int32', 'int64', 'float16', 'float32', 'float64']
    data_numeric = data.select_dtypes(include=numerics)
    for index, field in enumerate(data_numeric):
        date_male = data.query(f"sex == 'male'")
        date_female = data.query(f"sex == 'female'")
        ax[index].boxplot([date_male[field], date_female[field]], labels=['male', 'female'])
        ax[index].set_title(field)

    plt.grid()
    plt.show()


def show_4(data):
    # Меры центральной тенденции:-bmi
    fields = ['bmi', 'charges']
    fig, ax = plt.subplots(1, 2, figsize=(15, 6))
    for index, name in enumerate(fields):
        q1 = np.percentile(data[name], 25, interpolation='midpoint')
        q3 = np.percentile(data[name], 75, interpolation='midpoint')
        std = data[name].std()
        raz = data[name].max() - data[name].min()
        iqr = q3 - q1
        print('--------------Меры центральной тенденции---------------------')
        print(f"Среднее {name} = %f" % np.mean(data[name]))
        print(f"Мода {name} = ", sts.mode(data[name]))
        print(f"Медиана {name} = %f" % np.median(data[name]))
        print(f'--------------Меры изменчивости {name}---------------------')
        q1 = np.percentile(data[name], 25, interpolation='midpoint')
        q3 = np.percentile(data[name], 75, interpolation='midpoint')
        print("Стандартное отклонение: ", data[name].std())
        print("Размах: ", data[name].max() - data[name].min())
        print("Межквартильный размах: ", q3 - q1)
        ax[index].hist(data[name], label=name)
        ax[index].axvline(std, color='r', linestyle='dashed', linewidth=1, label='Стандартное отклонение')
        ax[index].axvline(raz, color='b', linestyle='dashed', linewidth=1, label='Размах')
        ax[index].axvline(iqr, color='y', linestyle='dashed', linewidth=1, label='Межквартильный размах')
        ax[index].axvline(np.mean(data[name]), color='orange', linestyle='dashed', linewidth=1,
                          label='Стандартное отклонение')
        ax[index].axvline(sts.mode(data[name])[0], color='purple', linestyle='dashed', linewidth=1, label='Размах')
        ax[index].axvline(np.median(data[name]), color='g', linestyle='dashed', linewidth=1,
                          label='Межквартильный размах')
        ax[index].legend()
        ax[index].grid()

    plt.show()


def show_3():
    data = pd.read_csv(file_location, sep=',')
    fig, ax = plt.subplots(1, 4, figsize=(15, 6))
    numerics = ['int16', 'int32', 'int64', 'float16', 'float32', 'float64']
    data_numeric = data.select_dtypes(include=numerics)
    for index, name in enumerate(data_numeric):
        ax[index].hist(data[name], label=name)
        for i in ax[index].patches:
            height = int(i.get_height())
            ax[index].annotate("{}".format(height),
                               xy=(i.get_x() + i.get_width() / 2, height),
                               xytext=(0, 3),
                               textcoords="offset points", size=10, color="Green",
                               ha='center', va='bottom')
        ax[index].legend()
        ax[index].grid()
    plt.show()
