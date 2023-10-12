package model.Command;

import model.Dao;

public class Add implements Order{
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Add(String num) {
        this.num = num;
    }

    @Override
    public boolean execute() {
        Dao dao=Dao.getInstance();
        return dao.reduce(num,false);
    }
}
