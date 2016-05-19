package org.course.project.utility.exceptions;

public class ParametersException extends Exception {

    private String msg;

    public ParametersException(final String msg) {

        this.msg = msg;
    }

    public String getMsg() {

        return this.msg;
    }

    public void setMsg(final String msg) {

        this.msg = msg;
    }
}
