package cn.ommiao.mowidgets.utils;


import java.util.ArrayList;

public class Step {

    private Notifier notifier = null;
    private ArrayList<Operation> operations = new ArrayList<>();
    private int curNo = 0;

    public Step() {
        notifier = new Notifier() {
            @Override
            public void success() {
                curNo++;
                if(curNo < operations.size()){
                    excute();
                }
            }
        };
    }

    public interface Operation{
        void start();
    }

    public Step then(Operation operation){
        operations.add(operation);
        return this;
    }

    public void excute(){
        if(operations.size() == 0){
            return;
        }
        Operation operation = operations.get(curNo);
        operation.start();
    }

    public Notifier getNotifier(){
        return notifier;
    }

    public interface Notifier{
        void success();
    }

}
