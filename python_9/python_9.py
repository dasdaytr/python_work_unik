from pathlib import Path

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats

import statsmodels.api as sm
from sklearn.neighbors import KNeighborsClassifier
from statsmodels.formula.api import ols
from statsmodels.stats.multicomp import pairwise_tukeyhsd
import sklearn
from sklearn.datasets import load_iris
from sklearn import preprocessing
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay, classification_report
from sklearn.svm import SVC
from sklearn.model_selection import GridSearchCV
location = Path(__file__).absolute().parent
file_location = location / 'home_insurance.csv'


def draw_confusion_matrix(y_predict, model):
    cm = confusion_matrix(y_test, y_predict, labels=model.classes_)
    disp = ConfusionMatrixDisplay(confusion_matrix=cm, display_labels=model.classes_)
    disp.plot()
    plt.show()


if __name__ == '__main__':
    data = pd.read_csv(file_location, sep=',')
    data = data.dropna(subset=['BUILDINGS_COVER'])
    data = data.sample(3000)
    print(data['BUILDINGS_COVER'].value_counts())
    encoder = preprocessing.LabelEncoder()

    predictors = data
    for i in predictors.columns:
        predictors[i] = encoder.fit_transform(predictors[i])
    target = predictors['BUILDINGS_COVER']

    x_train, x_test, y_train, y_test = train_test_split(predictors, target, train_size=0.8, random_state=271)
    model = LogisticRegression(random_state=271, max_iter=500)
    model.fit(x_train, y_train)
    y_predict = model.predict(x_test)
    print('Предсказанные значения:\t', y_predict[:10])
    print('Исходные значения\t', np.array(y_test[:10]))

    draw_confusion_matrix(y_predict, model)

    print(classification_report(y_predict, y_test))

    param_kernel = ('linear', 'rbf', 'poly', 'sigmoid')
    parameters = {'kernel': param_kernel}
    model = SVC()
    grid_search_svm = GridSearchCV(estimator=model, param_grid=parameters, cv=6)
    grid_search_svm.fit(x_train, y_train)

    best_model = grid_search_svm.best_estimator_
    print(best_model.kernel)
    svm_preds = best_model.predict(x_test)
    draw_confusion_matrix(svm_preds, best_model)
    print(classification_report(svm_preds, y_test))
    #-------------------------

    number_of_neighbors = np.arange(2, 11)
    model_KNN = KNeighborsClassifier()
    params = {"n_neighbors": number_of_neighbors}

    grid_search_knn = GridSearchCV(estimator=model_KNN, param_grid=params, cv=6)

    print(grid_search_knn.fit(x_train, y_train))

    print(grid_search_knn.best_score_)

    best_model = grid_search_knn.best_estimator_
    print(best_model)

    knn_preds = grid_search_knn.predict(x_test)
    draw_confusion_matrix(knn_preds, best_model)
    print(classification_report(knn_preds, y_test))