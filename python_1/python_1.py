from enum import Enum
from abc import ABC, abstractmethod
import itertools
import math


class Operation(Enum):
    ADDITION = 0
    SUBTRACTION = 1
    MULTIPLICATION = 2
    DIVISION = 3,
    SQUARE = 4


class Shape(ABC):
    @abstractmethod
    def get_area(self):
        pass


class Rectangle(Shape):
    def __init__(self, width, height):
        self.width = width
        self.height = height

    def get_area(self):
        return self.width * self.height


class Triangle(Shape):
    def __init__(self, base, height):
        self.base = base
        self.height = height

    def get_area(self):
        return (self.base * self.height) / 2


class Circle(Shape):
    def __init__(self, radios):
        self.radios = radios

    def get_area(self):
        return math.pi * self.radios


def print_work_1():
    print(get_area_shape(Circle(5)))
    print(calculate(5, 6, Operation.MULTIPLICATION))
    print(get_area_heron(5, 7, 8))


def get_area_shape(shape):
    return {type(shape).__name__: shape.get_area()}


def calculate(x, y, operation):
    match operation:
        case Operation.ADDITION:
            return x + y
        case Operation.DIVISION:
            return x - y
        case Operation.MULTIPLICATION:
            return x * y
        case Operation.SUBTRACTION:
            return x / y
        case Operation.SQUARE:
            return x ** y


def get_area_heron(a, b, c):
    p = (a + b + c) / 2
    return math.sqrt(p * (p - a) * (p - b) * (p - c))
