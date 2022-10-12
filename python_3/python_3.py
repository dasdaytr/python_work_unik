import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from pathlib import Path
import plotly
import plotly.graph_objs as go
import plotly.express as px

location = Path(__file__).absolute().parent
file_location = location / 'imdb_top_1000.csv'


def print_work_3():
    # https://www.kaggle.com/ сайт откуда был скачен датасет фильмов
    # show_bar_plotly()
    # show_box_plots_plotly()
    # show_line_chart_matplotlib()
    # show_bar_chart_matplotlib()
    # show_pie_chart_matplotlib()
    show_box_plots_matplotlib()


def show_bar_plotly():
    data = pd.read_csv(file_location, sep=',')
    print(data.info())
    print(plotly.__version__)
    x = np.linspace(-3, 3, 20)

    colors = ['lightslategray', ] * 5
    colors[1] = 'crimson'
    data = data.groupby(['Certificate']).sum(numeric_only=True)
    fig = go.Figure(px.bar(data,
                           x=data.index,
                           y='IMDB_Rating',
                           color="IMDB_Rating",
                           color_continuous_scale=["blue", "green", "red"],
                           text='IMDB_Rating',

                           # marker color can be a single color value or an iterable
                           ))
    fig.update_traces(textposition='outside')
    fig.update_layout(uniformtext_minsize=14, uniformtext_mode='show', )
    fig.update_layout(title={
        'text': "Test",
        'y': 0.97,
        'x': 0.5,
        'font_size': 20
    })
    min_sum = data['IMDB_Rating'].min()
    max_sum = data['IMDB_Rating'].sum()
    fig.update_yaxes(range=[min_sum - max_sum * 0.1, float(max_sum)])
    fig.update_xaxes(showgrid=True, gridwidth=1, gridcolor='Azure')
    fig.update_yaxes(showgrid=True, gridwidth=1, gridcolor='Azure')
    fig.write_html('first_figure.html', auto_open=False)


def show_pie_chart_plotly():
    data = pd.read_csv(file_location, sep=',')
    fig = px.pie(data, values='IMDB_Rating', names='Certificate', title='test')
    fig.update_layout(width=1000, height=1000, )
    fig.update_layout(title={
        'text': "Test",
        'y': 0.93,
        'x': 0.5,
        'font_size': 20
    })
    fig.write_html('first_figure.html', auto_open=False)


def show_line_chart_plotly():
    data = pd.read_csv(file_location, sep=',')
    data = data.sort_values(by='Released_Year')
    fig = px.line(data, y='Gross', x=['Released_Year'], title='test', color_discrete_sequence=['crimson'], markers=True)
    fig.update_layout(width=1000, height=1000)
    fig.update_traces(marker=dict(size=12, color='darkblue', line=dict(width=3,
                                                                       color='black')))
    fig.update_layout(title={
        'text': "Test",
        'y': 0.93,
        'x': 0.5,
        'font_size': 20
    })
    fig.update_layout(legend=dict(yanchor="bottom", y=0, xanchor="left", x=0.01))
    fig.write_html('first_figure.html', auto_open=False)


def show_box_plots_plotly():
    data = pd.read_csv(file_location, sep=',')
    data = data.sort_values(by='Released_Year')
    fig = px.box(data, y='IMDB_Rating')
    fig.update_layout(title={
        'text': "Пора заканчивать уже ящики с усами пошли ...",
        'y': 0.96,
        'x': 0.5,
        'font_size': 20
    })
    fig.write_html('first_figure.html', auto_open=False)


def show_bar_chart_matplotlib():
    data = pd.read_csv(file_location, sep=',')
    data = data.sort_values(by='Released_Year')
    data = data.groupby(['Certificate']).sum(numeric_only=True)
    col = []
    for val in data['IMDB_Rating']:
        if val < 250:
            col.append('red')
        elif 250 < val < 1000:
            col.append('blue')
        else:
            col.append('green')
    plt.bar(data.index, data['IMDB_Rating'], color=col)
    plt.xlabel('Сертификат', fontsize=14)
    plt.ylabel('IMDB_Rating сумма', fontsize=14)
    label = data['IMDB_Rating']

    for index, element in enumerate(label):
        plt.text(x=index, y=element, s=element,
                 size=10, color='red')
    plt.show()


def show_pie_chart_matplotlib():
    data = pd.read_csv(file_location, sep=',')
    data = data.sort_values(by='Released_Year')
    data = data.groupby(['Certificate']).sum(numeric_only=True)
    plt.title('Спасите от этих графиков.ААААААА', fontsize=20)
    plt.pie(data['IMDB_Rating'], autopct='%.1f%%', textprops={'fontsize': 14})
    plt.legend(labels=data.index, fontsize=18, loc='upper center',
               bbox_to_anchor=(0.5, -0.04), ncol=2)
    plt.show()


def show_line_chart_matplotlib():
    data = pd.read_csv(file_location, sep=',')
    data = data.sort_values(by='Released_Year')
    plt.title('Задолбали эти графики!!!!!!!!!!!!!', fontsize=30)
    plt.xlabel('Дата выхода', fontsize=10)
    plt.ylabel('Прибыль', fontsize=10)
    plt.plot(
        data['Released_Year'],
        data['Gross'],
        marker='.',
        color='black',
        markerfacecolor='red',
        markeredgecolor='black',
        markersize=23)
    plt.grid(True)
    plt.show()


def show_box_plots_matplotlib():
    data = pd.read_csv(file_location, sep=',')
    fig, ax1 = plt.subplots()
    ax1.set_title('AAAAAAAAAAAAAAAAAAAAAA')
    ax1.boxplot(data['IMDB_Rating'])
    plt.show()


def test():
    x = np.linspace(-3, 3, 20)
    fig = go.Figure()
    fig.add_trace(go.Scatter(x=x, y=x, name='f(x)'))
    fig.add_trace(go.Scatter(x=x, y=(x ** 2), name='f(x^2)'))
    fig.add_trace(go.Scatter(x=x, y=(x ** 3), name='f(x^3)'))
    fig.update_layout(title={
        'text': 'Вывод трех графиков на одном полотне',
        'font_size': 10,
        'x': 0.5,
        'xanchor': 'center',
        'yanchor': 'top'
    },
        xaxis=dict(title='x', title_font_size=16, tickfont_size=14),
        yaxis=dict(title='f(x)', title_font_size=26, tickfont_size=14),
        width=1000, height=1000,
        legend=dict(orientation="v", xanchor="center", yanchor="middle"))
    fig.write_html('first_figure.html', auto_open=False)
