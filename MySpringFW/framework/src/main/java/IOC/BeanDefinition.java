package IOC;

public class BeanDefinition {
    private String beanId;
    private Class clazz;
    public BeanDefinition(String id, Class clazz){
        this.beanId= id;
        this.clazz = clazz;
    }

    public String getBeanId() {
        return beanId;
    }

    public void setId(String id) {
        this.beanId = id;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Object getInstance(){
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}