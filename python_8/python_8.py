from pathlib import Path

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats

import statsmodels.api as sm
from statsmodels.formula.api import ols
from statsmodels.stats.multicomp import pairwise_tukeyhsd

location = Path(__file__).absolute().parent
file_location = location / 'insurance (1).csv'
if __name__ == '__main__':
    df = pd.read_csv(file_location, sep=',')
    print(df['sex'].unique())
    print(df['smoker'].unique())

    df['smoker'] = df['smoker'].apply(lambda x: 1 if x == 'yes' else 0)

    print(df['region'].unique())
    #2.Выполнить однофакторный ANOVA тест, чтобы проверить влияние
    #региона на индекс массы тела (BMI), используя первый способ, через
    #библиотеку Scipy.

    df2 = df[['region', 'bmi']]
    groups = df2.groupby('region').groups

    southwest = df2['bmi'][groups['southwest']]
    southeast = df2['bmi'][groups['southeast']]
    northwest = df2['bmi'][groups['northwest']]
    northeast = df2['bmi'][groups['northeast']]

    res = stats.f_oneway(southwest, southeast, northwest, northeast)
    print(res)
    #2 ----КОНЕЦ

    #3.Выполнить однофакторный ANOVA тест, чтобы проверить влияние
    #региона на индекс массы тела (BMI), используя второй способ, с помощью
    #функции anova_lm() из библиотеки statsmodels.
    model = ols('bmi ~ region', data=df2).fit()

    anova_result = sm.stats.anova_lm(model, typ=2)
    print(anova_result)
    #sum_sq
    #Сумма квадратов для модельных терминов.
    #
    #df
    #Степени свободы для модельных терминов.

    #F
    #F статистическое значение для значимости добавления модельных терминов.

    #PR(>F)
    #P-значение значимости добавления модельных терминов.
    #3---END


    #4.С помощью t критерия Стьюдента перебрать все пары. Определить поправку Бонферрони. Сделать выводы.
    unique_region = df2['region'].unique()
    region_pairs = []
    for region1 in range(len(unique_region)):
        for region2 in range(region1 + 1, (len(unique_region))):
            region_pairs.append((unique_region[region1], unique_region[region2]))

    for region1, region2 in region_pairs:
        print(region1, region2)
        print(stats.ttest_ind(df2['bmi'][groups[region1]], df2['bmi'][groups[region2]]))

    bonferroni = 0.05 / 6
    print("Поправка бонферони", bonferroni)

    #4----END

    #5.Выполнить пост-хок тесты Тьюки и построить график.
    tukey = pairwise_tukeyhsd(endog = df2['bmi'], groups = df2['region'], alpha = 0.05)
    tukey.plot_simultaneous()
    plt.vlines(x = 31.2, ymin = -0.5, ymax = 4.5, color = 'red')
    tukey.summary()
    plt.show()

    #Видим, что доверительные интервалы southwest и southeast находятся в разных облостях и не пересекаются с
    # northwest и northeast, поэтому пост-хок тесты
    # показали что различия между ними существенные.(подтверждение предыдущего метода проверки)

    #5-----END


    #6. Выполнить двухфакторный ANOVA тест, чтобы проверить влияние региона и пола на индекс
    # массы тела (BMI), используя функцию anova_lm() из библиотеки statsmodels.

    model = ols('bmi ~ C(region) + C(sex) + C(region):C(sex)', data=df).fit()
    res = sm.stats.anova_lm(model, typ=2)
    print('6---')


    #7.Выполнить пост-хок тесты Тьюки и построить график.
    df['combination'] = df.region + " / " + df.sex
    tukey = pairwise_tukeyhsd(endog=df['bmi'], groups=df['combination'], alpha=0.05)
    tukey.plot_simultaneous()
    plt.vlines(x=31.5, ymin=-0.5, ymax=10, color='red')
    res = tukey.summary()
    print('7---END', res)
    plt.show()
