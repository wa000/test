package test.analysis;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.Perceptron;

import java.util.Arrays;
/**
 * Created by 阿龙 on 2017/1/24.
 */
public class CalculatePerceptron{
    public static void main(String args[]){
//        建立AND训练集
        DataSet trainAndSet = new DataSet(2, 1);
        trainAndSet.addRow(new DataSetRow(new double[]{0, 0}, new double[]{0}));
        trainAndSet.addRow(new DataSetRow(new double[]{0, 1}, new double[]{0}));
        trainAndSet.addRow(new DataSetRow(new double[]{1, 0}, new double[]{0}));
        trainAndSet.addRow(new DataSetRow(new double[]{1, 1}, new double[]{1}));

//        建立OR训练集
        DataSet trainOrSet = new DataSet(2, 1);
        trainOrSet.add(new DataSetRow(new double[]{0, 0}, new double[]{0}));
        trainOrSet.add(new DataSetRow(new double[]{0, 1}, new double[]{1}));
        trainOrSet.add(new DataSetRow(new double[]{1, 0}, new double[]{1}));
        trainOrSet.add(new DataSetRow(new double[]{1, 1}, new double[]{1}));

//        建立XOR训练集
        DataSet trainXorSet = new DataSet(2, 1);
        trainXorSet.addRow(new DataSetRow(new double[]{0, 0}, new double[]{0}));
        trainXorSet.addRow(new DataSetRow(new double[]{0, 1}, new double[]{1}));
        trainXorSet.addRow(new DataSetRow(new double[]{1, 0}, new double[]{1}));
        trainXorSet.addRow(new DataSetRow(new double[]{1, 1}, new double[]{0}));

//        建立感知机
        NeuralNetwork myPerceptron = new Perceptron(2, 1);
//        训练AND集
        myPerceptron.learn(trainAndSet);
        System.out.println("测试感知机AND集训练结果：");
        myPerceptron.save("AND_learn_result.nnet");
        testNeuralNetwork(myPerceptron, trainAndSet);

//        训练OR集
        myPerceptron.learn(trainOrSet);
        System.out.println("测试感知机Or集训练结果：");
        myPerceptron.save("OR_learn_result.nnet");
        testNeuralNetwork(myPerceptron, trainOrSet);

//        训练XOR集
//        由于XOR输入输出情况线性不可分，将无法完成训练
        myPerceptron.learn(trainXorSet);
        System.out.println("测试感知机Xor集训练结果：");
        testNeuralNetwork(myPerceptron, trainXorSet);


    }
    public static void testNeuralNetwork(NeuralNetwork nnet, DataSet tset) {

            for (DataSetRow dataRow : tset.getRows()) {

                   nnet.setInput(dataRow.getInput());
                   nnet.calculate();
                   double[ ] networkOutput = nnet.getOutput();
                   System.out.print("Input: " + Arrays.toString(dataRow.getInput()) );
                   System.out.println(" Output: " + Arrays.toString(networkOutput) );
                 }
           }
}