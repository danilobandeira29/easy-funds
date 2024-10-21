package com.github.danilobandeira29.easy_funds.entities;

public interface IPayeeStrategy {
    String getDocument();
    String getEmail();
    PayeeEnum getType();
    String getName();
}
