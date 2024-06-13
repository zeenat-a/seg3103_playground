import pytest
from simple_calculator import Calculator

def test_addition():
    calc = Calculator()
    assert calc.add(2, 3) == 5

def test_subtraction():
    calc = Calculator()
    assert calc.subtract(5, 3) == 2

def test_multiplication():
    calc = Calculator()
    assert calc.multiply(2, 3) == 6

def test_division():
    calc = Calculator()
    assert calc.divide(6, 3) == 2

def test_division_by_zero():
    calc = Calculator()
    with pytest.raises(ZeroDivisionError):
        calc.divide(6, 0)

def test_power():
    calc = Calculator()
    assert calc.power(2, 3) == 8

