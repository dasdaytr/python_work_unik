from pathlib import Path
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import sklearn.linear_model as lm
from sklearn.preprocessing import StandardScaler

location = Path(__file__).absolute().parent
file_location_house = location / 'housePrice.csv'
file_location_bitcoin = location / 'bitcoin.csv'


def print_work_7():
    # print_1_2()
    print_3_11()
    #print13_16()


def print_1_2():
    data = pd.DataFrame({
        'День': ['Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница'],
        'Улица': [80, 98, 75, 91, 78],
        'Гараж': [100, 82, 105, 89, 102]
    })
    # Подсчитать корреляцию по Пирсону

    data_1 = data['Улица']
    data_2 = data['Гараж']

    print(data)
    print(np.corrcoef(data_1, data_2))
    print(data['Улица'].corr(data['Гараж']))

    plt.grid(True)
    plt.title('Диаграмма рассеяния', fontsize=20)
    plt.xlabel('Гараж')
    plt.ylabel('Улица')

    plt.scatter(data_2, data_1, marker='o', color='crimson')
    plt.show()


def print_3_11():
    data = pd.read_csv(file_location_bitcoin, sep=',')
    data['predict'] = data['close'].shift(-14)
    data = data[:14]

    data_x = data[['close']]
    data_y = data[['predict']]

    regression = lm.LinearRegression()

    regression.fit(data_x, data_y)

    print(f"Наклон линии регрессии -->{regression.coef_}")
    print(f"y-перехвата ---> {regression.intercept_}")
    print(f"Точность прознозируемой цены закрытия --> {regression.score(data_x,data_y)}")
    print(f"Предсказание стоймости крипты за последение 14 дней ---> {regression.predict(data[['close']][-14:])}")

    plt.scatter(data_x, data_y, color='m', marker='o', s=30)

    plt.plot(data_x, regression.predict(data_x), color='g')

    plt.xlabel('x')
    plt.ylabel('y')

    plt.show()
    print(data)


def print13_16():
    data = pd.read_csv(file_location_house, sep=',')

    data['Area'] = pd.to_numeric(data['Area'], errors='coerce')
    data.fillna(data['Area'].mean(), inplace=True)

    data_x = data['Area']
    data_y = data['Price(USD)']

    n = np.size(data_x)

    m_x = np.mean(data_x)
    m_y = np.mean(data_y)
    Ss_closePredict = np.sum(data_y * data_x) - n * m_x * m_y
    Ss_closeClose = np.sum(data_x * data_x) - n * m_x * m_x

    b_1 = Ss_closePredict / Ss_closeClose
    b_0 = m_y - b_1 * m_x

    plt.scatter(x=data_x, y=data_y, color='m', marker='o', alpha=0.3)

    y_pred = b_0 + b_1 * data_x

    print(f"y-перехвата={y_pred}")
    plt.plot(data_x, y_pred, color='g')

    plt.xlabel('x')
    plt.ylabel('y')

    plt.show()
