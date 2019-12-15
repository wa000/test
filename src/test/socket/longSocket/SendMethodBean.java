package test.socket.longSocket;

import java.io.Serializable;

public class SendMethodBean implements Serializable {

    private static final long serialVersionUID = 5198564317744016914L;

    private String methodName;

    private String paramClassName;

    private String paramValue;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParamClassName() {
        return paramClassName;
    }

    public void setParamClassName(String paramClassName) {
        this.paramClassName = paramClassName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SendMethodBean{");
        sb.append("methodName='").append(methodName).append('\'');
        sb.append(", paramClassName='").append(paramClassName).append('\'');
        sb.append(", paramValue='").append(paramValue).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
