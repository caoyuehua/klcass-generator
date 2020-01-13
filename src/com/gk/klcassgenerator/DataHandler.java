package com.gk.klcassgenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 数据处理器
 * @author yvette
 *
 */
public class DataHandler {
  // 选科list处理 Start
  public static List<Option> initOption(List<Option> optionList) {
    // 验证输入数据，不符合标准抛出异常
    if (optionList == null || optionList.size() == 0) {
      throw new DataException(ErrorCode.OPTION_NOTHING);
    }
    for (Option option : optionList) {
      if (option.getSubject() == null) {
        throw new DataException(ErrorCode.OPTION_SUBJECT_NULL);
      }
      if (option.getTotalNumber() <= 0) {
        throw new DataException(ErrorCode.OPTION_TOTALNUMBER_LE_ZERO);
      }
      if (option.getDefaultKlassSize() <= 0) {
        throw new DataException(ErrorCode.OPTION_DEFAULTKLASSSIZE_LE_ZERO);
      }
      if (option.getDefaultShiftNumber() <= 0) {
        throw new DataException(ErrorCode.OPTION_DEFAULTSHIFTNUMBER_LE_ZERO);
      }
      if (option.getCourseHour() <= 0) {
        throw new DataException(ErrorCode.OPTION_COURSEHOUR_LE_ZERO);
      }
    }

    return optionList;
  }
  public static List<Option> calOption4KclassNumberAndKclassSize(List<Option> optionList) {
    // 处理选科list， 计算选科的班级个数、班级人数
    for (Option option : optionList) {
      // 总人数、预设班级人数、预设插班人数
      int totalNumber = option.getTotalNumber();
      int defaultKlassSize = option.getDefaultKlassSize();
      int defaultShiftNumber = option.getDefaultShiftNumber();
      // 班级个数
      int klassNumber = Math.round(totalNumber / (float) defaultKlassSize);
      // 剩余人数
      int surplusNumber = totalNumber - klassNumber * defaultKlassSize;
      // 实际插班人数
      int shiftNumber = (int) Math.ceil(surplusNumber / (float) klassNumber);
      if (shiftNumber > defaultShiftNumber) {
        klassNumber += 1;
      }
      // 班级人数
      int klassSize = (int) Math.ceil(totalNumber / (float) klassNumber);
      // 设置选科的班级个数、班级人数
      option.setKlassNumber(klassNumber);
      option.setKlassSize(klassSize);
    }
    return optionList;
  }
  public static List<Klass> initKlass(List<Option> optionList) {
    // 根据选科list初始化班级list，设置班级的名称、科目、班级人数、课程课时
    List<Klass> klassList = new ArrayList<Klass>();
    optionList = DataHandler.calOption4KclassNumberAndKclassSize(optionList);
    for (Option option : optionList) {
      // 科目
      Subject subject = option.getSubject();
      // 班级个数
      int klassNumber = option.getKlassNumber();
      // 班级人数
      int klassSize = option.getKlassSize();
      // 课程课时
      int courseHour = option.getCourseHour();
      for (int i = 0; i < klassNumber; i++) {
        Klass klass = new Klass();
        klass.setName(subject.getName() + (i + 1) + "班");
        klass.setSubject(subject);
        klass.setKlassSize(klassSize);
        klass.setCourseHour(courseHour);
        klassList.add(klass);
      }
    }
    return klassList;
  }
  public static Map<Subject, Integer> initKlassSizeMap(List<Option> optionList) {
    optionList = DataHandler.calOption4KclassNumberAndKclassSize(optionList);
    // 初始化班级人数map，获取选科的科目、班级人数，用于设置组合的选科的科目的班级人数
    Map<Subject, Integer> klassSizeMap = new HashMap<Subject, Integer>();
    for (Option option : optionList) {
      klassSizeMap.put(option.getSubject(), option.getKlassSize());
    }
    return klassSizeMap;
  }
  //选科list处理 End
  //组合list处理 Start
  public static List<Combination> initCombination(List<Option> optionList, List<Combination> combinationList) {
    // 初始化组合list， 设置组合的选科的科目、总人数、班级人数
    Map<Subject, Integer> klassSizeMap = DataHandler.initKlassSizeMap(optionList);
    // 设置组合的选科的科目、总人数、班级人数
    // 验证输入数据，不符合标准抛出异常
    if (combinationList == null || combinationList.size() == 0) {
      throw new DataException(ErrorCode.COMBINATION_NOTHING);
    }
    for (Combination combination : combinationList) {
      List<Option> opList = combination.getOptionList();
      if (opList == null || opList.size() == 0) {
        throw new DataException(ErrorCode.COMBINATION_OPTION_NOTHING);
      }
      for (Option op : opList) {
        if (op.getSubject() == null) {
          throw new DataException(ErrorCode.COMBINATION_OPTION_SUBJECT_NULL);
        }
        if (op.getTotalNumber() <= 0) {
          throw new DataException(ErrorCode.COMBINATION_OPTION_TOTALNUMBER_LE_ZERO);
        }
        op.setKlassSize(klassSizeMap.get(op.getSubject()));
      }
    }
    return combinationList;
  }
  //计算处理顺序  Start
  public static TotalNumberDetail initTotalNumberDetail(List<Combination> combinationList) {
    // 先设置组合的总人数map和总人数list
    List<Integer> totalNumberList = new ArrayList<Integer>();
    // 遍历组合
    for (Combination combination : combinationList) {
      // 初始化总人数map
      Map<Integer, Integer> totalNumberMap = new HashMap<Integer, Integer>();
      // 取出组合中的选科list
      List<Option> optionList = combination.getOptionList();
      // 遍历选科
      for (Option option : optionList) {
        // 取出选科中的总人数
        int totalNumber = option.getTotalNumber();
        if (totalNumber > 0) {
          // 选科中的总人数不在组合总人数中
          if (!totalNumberMap.containsKey(totalNumber)) {
            // 将选科中的总人数放入组合总人数中，出现次数记为1
            totalNumberMap.put(totalNumber, 1);
          } else {// 选科中的总人数在组合总人数中
            // 将选科中的总人数放入组合总人数中，出现次数增加1
            totalNumberMap.put(totalNumber, totalNumberMap.get(totalNumber) + 1);
          }
          // 选科中的总人数不在总人数list中
          if (!totalNumberList.contains(totalNumber)) {
            // 将选科中的总人数放入总人数list中
            totalNumberList.add(totalNumber);
          }
        }
      }
      // 设置组合的总人数map
      combination.setTotalNumberMap(totalNumberMap);
    }
    // 实现排序，从大到小
    Collections.sort(totalNumberList, Collections.reverseOrder());
    TotalNumberDetail totalNumberDetail = new TotalNumberDetail();
    totalNumberDetail.setCombinationList(combinationList);
    totalNumberDetail.setTotalNumberList(totalNumberList);
    return totalNumberDetail;
  }
  public static List<Combination> findCombinationListByTotalNumber(List<Combination> combinationList,
      int totalNumber) {
    // 获取包含当前总人数的组合list
    List<Combination> subCombinationList = new ArrayList<Combination>();
    for (Combination combination : combinationList) {
      // 总人数map
      Map<Integer, Integer> totalNumberMap = combination.getTotalNumberMap();
      // 包含当前总人数
      if (totalNumberMap.containsKey(totalNumber)) {
        // 设置组合的当前总人数，用于自定义比较组合的大小
        combination.setTotalNumberNumber(totalNumberMap.get(totalNumber));
        // 将组合放入list
        subCombinationList.add(combination);
      }
    }
    // 实现排序，按照当前总人数从大到小
    Collections.sort(subCombinationList, new Comparator<Combination>() {
      @Override
      public int compare(Combination c1, Combination c2) {
        if (c1.getTotalNumberNumber() > c2.getTotalNumberNumber()) {
          // return -1:即为正序排序
          return -1;
        } else if (c1.getTotalNumberNumber() == c1.getTotalNumberNumber()) {
          return 0;
        } else {
          // return 1: 即为倒序排序
          return 1;
        }
      }
    });
    return subCombinationList;
  }
  public static List<Combination> calCombination4OrderOfProcessing(List<Combination> combinationList) {
    // 先设置组合的总人数map和总人数list
    TotalNumberDetail totalNumberDetail = DataHandler.initTotalNumberDetail(combinationList);
    List<Integer> totalNumberList = totalNumberDetail.getTotalNumberList();
    combinationList = totalNumberDetail.getCombinationList();

    // 总人数list
    int orderOfProcessing = 0;
    for (Integer totalNumber : totalNumberList) {
      List<Combination> subCombinationList = DataHandler.findCombinationListByTotalNumber(combinationList,
          totalNumber);
      // 遍历组合
      for (Combination subCombination : subCombinationList) {
        // 找到当前组合在原组合list中的索引
        int index = combinationList.indexOf(subCombination);
        Combination combination = combinationList.get(index);
        // 没有设置过处理顺序
        if (combination.getOrderOfProcessing() == 0) {
          // 设置组合的处理顺序
          orderOfProcessing++;
          combination.setOrderOfProcessing(orderOfProcessing);
        }
      }
    }

    // 实现排序，按照处理顺序从小到大
    Collections.sort(combinationList, new Comparator<Combination>() {
      @Override
      public int compare(Combination c1, Combination c2) {
        if (c1.getOrderOfProcessing() < c2.getOrderOfProcessing()) {
          // return -1:即为正序排序
          return -1;
        } else if (c1.getOrderOfProcessing() == c1.getOrderOfProcessing()) {
          return 0;
        } else {
          // return 1: 即为倒序排序
          return 1;
        }
      }
    });
    return combinationList;
  }
  // 计算处理顺序 End
  public static List<Combination> calCombination4maxKlassNumber(List<Combination> combinationList) {
    for (Combination combination : combinationList) {
      List<Option> optionList = combination.getOptionList();
      for (Option option : optionList) {
        // 总人数、班级人数
        int totalNumber = option.getTotalNumber();
        int klassSize = option.getKlassSize();
        // 计算班级个数
        int klassNumber = (int) Math.floor(totalNumber / (float) klassSize);
        // 设置选科的班级个数
        option.setKlassNumber(klassNumber);
      }
      // 实现排序，按照班级人数从大到小
      Collections.sort(optionList, new Comparator<Option>() {
        @Override
        public int compare(Option c1, Option c2) {
          if (c1.getKlassNumber() > c2.getKlassNumber()) {
            // return -1:即为正序排序
            return -1;
          } else if (c1.getKlassNumber() == c1.getKlassNumber()) {
            return 0;
          } else {
            // return 1: 即为倒序排序
            return 1;
          }
        }
      });
    }
    return combinationList;
  }
  public static List<Klass> addKlass(List<Klass> klassList, List<Klass> newKlassList, Option option,
      int surplusNumber, int groupId, Combination combination) {
    // 通过迭代器移除元素
    Iterator<Klass> iterator = klassList.iterator();
    while (iterator.hasNext()) {
      Klass item = iterator.next();
      if (option.getSubject() == item.getSubject()) {
        int stuNumber = 0;
        if (surplusNumber < 0) {
          stuNumber = option.getTotalNumber();
          item.setIsFull(0);
          item.setCanAgainJoinNumber(-surplusNumber);
        } else {
          stuNumber = option.getKlassSize();
          item.setIsFull(1);
          item.setCanAgainJoinNumber(0);
        }
        // 学生分组list
        Group group = new Group();
        group.setId(groupId);
        group.setStuNumber(stuNumber);
        group.setStuList(DataHandler.selectStusInturn(option, stuNumber, combination));
        List<Group> groupList = new ArrayList<Group>();
        groupList.add(group);
        item.setGroupList(groupList);
        newKlassList.add(item);
        iterator.remove();
        break;
      }
    }
    return newKlassList;
  }
  public static NotFullKlassDetail initNotFullKlassDetail(List<Klass> newKlassList, Subject subject) {
    List<Klass> notFullKlassList = new ArrayList<Klass>();
    int sumCanAgainJoinNumber = 0;
    for (Klass newKlass : newKlassList) {
      if (newKlass.getSubject() == subject && newKlass.getIsFull() == 0) {
        sumCanAgainJoinNumber += newKlass.getCanAgainJoinNumber();
        notFullKlassList.add(newKlass);
      }
    }
    // 实现排序，按照可再容纳人数从大到小
    Collections.sort(notFullKlassList, new Comparator<Klass>() {
      @Override
      public int compare(Klass c1, Klass c2) {
        if (c1.getCanAgainJoinNumber() > c2.getCanAgainJoinNumber()) {
          // return -1:即为正序排序
          return -1;
        } else if (c1.getCanAgainJoinNumber() == c1.getCanAgainJoinNumber()) {
          return 0;
        } else {
          // return 1: 即为倒序排序
          return 1;
        }
      }
    });
    NotFullKlassDetail notFullKlassDetail = new NotFullKlassDetail();
    notFullKlassDetail.setNotFullKlassList(notFullKlassList);
    notFullKlassDetail.setSumCanAgainJoinNumber(sumCanAgainJoinNumber);
    return notFullKlassDetail;
  }
  public static List<Klass> updateKlass(List<Klass> notFullKlassList, List<Klass> newKlassList,
      Option option, int groupId, Combination combination) {
    // 能够容纳，将学生分组加入到班级，计算班级可容纳人数
    for (Klass notFullKlass : notFullKlassList) {
      int index = newKlassList.indexOf(notFullKlass);
      Klass item = newKlassList.get(index);
      int surplusNumber = option.getTotalNumber() - item.getCanAgainJoinNumber();
      int stuNumber = 0;
      if (surplusNumber < 0) {
        stuNumber = option.getTotalNumber();
        item.setIsFull(0);
        item.setCanAgainJoinNumber(-surplusNumber);
        option.setTotalNumber(0);
      } else {
        stuNumber = item.getCanAgainJoinNumber();
        item.setIsFull(1);
        item.setCanAgainJoinNumber(0);
        option.setTotalNumber(surplusNumber);
      }
      Group group = new Group();
      group.setId(groupId);
      group.setStuNumber(stuNumber);
      group.setStuList(DataHandler.selectStusInturn(option, stuNumber, combination));
      List<Group> groupList = item.getGroupList();
      groupList.add(group);
      item.setGroupList(groupList);
    }
    return newKlassList;
  }
  public static List<Klass> calKlass4GroupList(DataInput dataInputImpl) {
    List<Option> optionInputList = DataHandler.initOption(dataInputImpl.initOption());
    List<Combination> combinationInputList = 
        DataHandler.initCombination(optionInputList,dataInputImpl.initCombination());
    
    List<Klass> klassList = DataHandler.initKlass(optionInputList);
    List<Klass> newKlassList = new ArrayList<Klass>();
    int groupId = 0;
    // 遍历组合 第一轮
    List<Combination> combinationList = 
        DataHandler.calCombination4maxKlassNumber(
            DataHandler.calCombination4OrderOfProcessing(combinationInputList));
    for (Combination combination : combinationList) {
      List<Option> optionList = combination.getOptionList();
      // 生成班级
      // 找到最大的班级个数
      int maxKlassNumber = optionList.get(0).getKlassNumber();
      // 从班级list中拿出一个班级，将学生分组加入到班级，计算班级可容纳人数
      for (int i = 0; i < maxKlassNumber; i++) {
        combination.setStuList(new ArrayList<Student>());
        groupId++;
        for (Option option : optionList) {
          int surplusNumber = option.getTotalNumber() - maxKlassNumber * option.getKlassSize();
          newKlassList = DataHandler.addKlass(klassList, newKlassList, option, surplusNumber, groupId, combination);
        }
      }
      // 重置选科的总人数、班级个数
      for (Option option : optionList) {
        // 重置选科的总人数、班级个数
        option.setTotalNumber(option.getTotalNumber() - maxKlassNumber * option.getKlassSize());
        option.setKlassNumber(0);
      }
      // 重置组合的选科、总人数map、当前总人数、处理顺序
      combination.setOptionList(optionList);
      combination.setTotalNumberMap(null);
      combination.setTotalNumberNumber(0);
      combination.setOrderOfProcessing(0);
    }

    combinationList = DataHandler.calCombination4OrderOfProcessing(combinationList);
    // 遍历组合 第二轮
    for (Combination combination : combinationList) {
      List<Option> optionList = combination.getOptionList();
      combination.setStuList(new ArrayList<Student>());
      groupId++;
      for (Option option : optionList) {
        Subject subject = option.getSubject();
        if (option.getTotalNumber() > 0) {
          // 找到这个科目的不满班
          NotFullKlassDetail notFullKlassDetail = DataHandler.initNotFullKlassDetail(newKlassList, subject);
          List<Klass> notFullKlassList = notFullKlassDetail.getNotFullKlassList();
          int sumCanAgainJoinNumber = notFullKlassDetail.getSumCanAgainJoinNumber();
          // 判断这个科目是否有不满班出现
          if (notFullKlassList.size() == 0) {
            // 没有出现，从班级list中拿出一个班级,，将学生分组加入到班级，计算班级可容纳人数
            int surplusNumber = option.getTotalNumber() - option.getKlassSize();
            newKlassList = DataHandler.addKlass(klassList, newKlassList, option, surplusNumber, groupId, combination);
          } else {
            // 已经出现，该班级是否能容纳这批学生
            if (sumCanAgainJoinNumber >= option.getTotalNumber()) {
              // 能够容纳，将学生分组加入到班级，计算班级可容纳人数
              newKlassList = DataHandler.updateKlass(notFullKlassList, newKlassList, option, groupId, combination);
            } else {
              int surplusNumber = option.getTotalNumber() - option.getKlassSize();
              newKlassList = DataHandler.addKlass(klassList, newKlassList, option, surplusNumber, groupId, combination);
            }
          }
        }
      }
    }
    return newKlassList;
  }
  //组合list处理 End
  public static void outNewKlassList(List<Klass> newKlassList) {
    for (Klass newKlass : newKlassList) {
      String name = newKlass.getName();
      System.out.println("name=" + name);
      String subjectName = newKlass.getSubject().getName();
      System.out.println("subjectName=" + subjectName);
      int klassSize = newKlass.getKlassSize();
      System.out.println("klassSize=" + klassSize);
      int courseHour = newKlass.getCourseHour();
      System.out.println("courseHour=" + courseHour);
      int isFull = newKlass.getIsFull();
      System.out.println("isFull=" + isFull);
      int canAgainJoinNumber = newKlass.getCanAgainJoinNumber();
      System.out.println("canAgainJoinNumber=" + canAgainJoinNumber);
      List<Group> groupList = newKlass.getGroupList();
      for (Group group : groupList) {
        int id = group.getId();
        System.out.println("id=" + id);
        int stuNumber = group.getStuNumber();
        System.out.println("stuNumber=" + stuNumber);
        List<Student> stuList= group.getStuList();
        for (Student student : stuList) {
          String code = student.getCode();
          System.out.println("code=" + code);
          String stuName = student.getName();
          System.out.println("stuName=" + stuName);
        }
      }
      System.out.println("caoyuehuacaoyuehuacaoyuehua");
    }
  }
  public static List<Student> selectStusInturn(Option option, int stuNumber, Combination combination) {
    List<Student> refStuList = combination.getStuList();
    List<Student> stuList = option.getStuList();
    List<Student> newStuList = new ArrayList<Student>();
    Iterator<Student> refIt = refStuList.iterator();
    while (refIt.hasNext()) {
      Student item = refIt.next();
      if (stuList.contains(item) && newStuList.size() < stuNumber) {
        newStuList.add(item);
      }
    }
    Iterator<Student> it = stuList.iterator();
    while (it.hasNext()) {
      Student item = it.next();
      if (newStuList.contains(item)) {
        it.remove();
      }
    }
    if (newStuList.size() < stuNumber) {
      Iterator<Student> itagin = stuList.iterator();
      while (itagin.hasNext()) {
        Student item = itagin.next();
        if (newStuList.size() < stuNumber) {
          newStuList.add(item);
          refStuList.add(item);
          itagin.remove();
        }
      }
    }
    combination.setStuList(refStuList);
    return newStuList;
  }
  public static List<Klass> findKlassByStuCode(List<Klass> newKlassList, String code) {
    List<Klass> klassList = new ArrayList<>();
    for (Klass newKlass : newKlassList) {
      List<Group> groupList = newKlass.getGroupList();
      for (Group group : groupList) {
        List<Student> stuList= group.getStuList();
        for (Student student : stuList) {
          if (code.equals(student.getCode())) {
            klassList.add(newKlass);
          }
        }
      }
    }
    return klassList;
  }
  public static List<Klass> findKlassByGroupId(List<Klass> newKlassList, int id) {
    List<Klass> klassList = new ArrayList<>();
    for (Klass newKlass : newKlassList) {
      List<Group> groupList = newKlass.getGroupList();
      for (Group group : groupList) {
        if (id == group.getId()) {
          klassList.add(newKlass);
        }
      }
    }
    return klassList;
  }
}