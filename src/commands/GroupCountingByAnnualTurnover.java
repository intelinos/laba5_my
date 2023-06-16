package commands;

import Organization.Organization;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;

import java.util.*;

/**
 * Класс команды group_counting_by_annual_turnover, которая сгруппировывает элементы коллекции по значению поля annualTurnover, выводит количество элементов в каждой группе.
 */
public class GroupCountingByAnnualTurnover extends Command{
    CollectionManager collectionManager;
    public GroupCountingByAnnualTurnover(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.needScanner = false;
    }

    @Override
    public String getInfo() {
        return "Сгруппировывает элементы коллекции по значению поля annualTurnover, выводит количество элементов в каждой группе.";
    }

    @Override
    public String getName() {
        return "group_counting_by_annual_turnover";
    }

    /**
     * Проверяет количество аргументов комманды и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если были введены аргументы команды.
     */
    @Override
    public void execute(String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length>1) throw new WrongNumberOfArgumentsException();
        Map<Integer, Organization> collection =collectionManager.getCollection();
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста!");
            return;
        }
        List<Float> turnoverList = new LinkedList<>();
        for (Map.Entry<Integer, Organization> pair : collection.entrySet()) {
            turnoverList.add(pair.getValue().getAnnualTurnover());
        }
        Collections.sort(turnoverList);
        int frequencyOfAnnualTurnover0 = Collections.frequency(turnoverList, turnoverList.get(0));
        System.out.println(turnoverList.get(0) + ": " + frequencyOfAnnualTurnover0);
        for (int i=0;i<turnoverList.size();i++) {
            if (i>0 && !turnoverList.get(i).equals(turnoverList.get(i-1))) {
                int frequencyOfAnnualTurnover = Collections.frequency(turnoverList, turnoverList.get(i));
                System.out.println(turnoverList.get(i) + ": " + frequencyOfAnnualTurnover);
            }
        }
    }
}
