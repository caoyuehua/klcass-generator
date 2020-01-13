这是一个Java Project，DataInputImplExample 右键Run As Java Application。

public static List<Klass> calKlass4GroupList(DataInput dataInputImpl)分析：
initOption(dataInputImpl.initOption());
initCombination(optionInputList,dataInputImpl.initCombination());
--initKlassSizeMap(optionList);
----calOption4KclassNumberAndKclassSize(optionList);
initKlass(optionInputList);
--calOption4KclassNumberAndKclassSize(optionList);
calCombination4maxKlassNumber(calCombination4OrderOfProcessing(combinationInputList));
--calCombination4OrderOfProcessing(combinationInputList)
----initTotalNumberDetail(combinationList);
----findCombinationListByTotalNumber(combinationList, totalNumber);
addKlass(klassList, newKlassList, option, surplusNumber, groupId);
initNotFullKlassDetail(newKlassList, subject);
updateKlass(notFullKlassList, newKlassList, option, groupId);

基本数据类型的运算
基础类库：Math类
集合
枚举类
接口和抽象类
static
自定义异常

有多少班，班级容量是多少，每个班有几个学生分组，每个学生分组有多少学生，有哪些学生。
源数据与过程数据分离，抽出输入数据和输出数据，自定义异常处理，检测输入数据。

感想：
码算法真的挺累，满脑子都是怎么实现，卡住时屡次欲放弃，所幸一直坚持，希望不远矣。

// 生成一串不重复的随机数
Set<Integer> indexSet = new LinkedHashSet<Integer>();
Random random = new Random();
while (indexSet.size() <= stuNumber) {
  indexSet.add(random.nextInt(stuList.size()));
}
System.out.println(indexSet);

验证每个学生的班级数以及所在的班级是否跟志愿一致。
验证组号相同的学生分组大部分学生相同。