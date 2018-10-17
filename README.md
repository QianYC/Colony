Colony
===
2018-10-16更新

### 0. 进度
- [x] SR1.1
- [ ] SR1.2
- [x] SR2.1
- [ ] SR2.2
- [ ] SR3.1
- [ ] SR4.1
- [ ] SR4.2
- [ ] SR4.3
- [ ] SR5.1
- [ ] SR5.2
- [ ] SR6.1
- [ ] SR7.1
- [ ] SR8.1
- [ ] SR9.1
- [ ] SR9.2

### 1. 业务需求：实现一个多人/AI实时对战系统
游戏逻辑：
- 游戏采取回合制。
- 开局玩家随机获得一个资源，然后每个回合玩家可以扩张自身已有资源，新占领的资源必须和原有资源接壤。
- 未被占领的资源可以被立即占有，占领已被占领的资源则需要消耗若干个回合的时间，这段时间内不能做出其他行为，且该时间随着占领时间增加而减少。
- 当达到一定游戏时间或者所有玩家均不能行动时，游戏结束。
- 若某个玩家的领地被其他玩家包围，该玩家直接死亡，计算成绩。

### 2. 用户需求
|id|描述|
|---|---|
|UR1|用户可以加入比赛，无需注册账号|
|UR2|未加入比赛的用户可以观看比赛|
|UR3|用户可以查看游戏地图|
|UR4|用户在每回合可以选择占领一个资源，也可以处于等待状态，若用户的行为不合法，则什么也不做|
|UR5|用户可以查看成绩排行榜|
|UR6|管理员可以设置比赛的时间、每回合时长、地图大小以及系统配置内容|
|UR7|管理员可以开放、结束比赛报名|
|UR8|管理员可以开始比赛|
|UR9|管理员可以查看选手信息、比赛过程|

### 3. 系统级需求
|UR|id|描述|
|---|---|---|
|UR1|SR1.1|对于参加比赛的用户，系统为其分配一个唯一标识符，该标识符用于验证用户的其他操作|
|   |SR1.2|每局比赛系统都要重新分配标识符|
|UR2|SR2.1|系统应该可以区分参赛选手和观众|
|   |SR2.2|系统要对外展示比赛情况|
|UR3|SR3.1|系统要保存游戏地图|
|UR4|SR4.1|系统应该每隔一定时间开始新的回合，通知所有参赛选手做出决策|
|   |SR4.2|对于参赛选手发送过来的指令，系统应该采取先来先服务的策略处理|
|   |SR4.3|若参赛选手在规定的时间段内未能做出决策，系统应该忽略迟到的指令|
|UR5|SR5.1|系统计算选手的分数，每隔一定时间更新一次排行榜|
|   |SR5.2|系统持久化历史比赛的成绩|
|UR6|SR6.1|系统可以动态更改配置|
|UR7|SR7.1|系统可以接受用户注册比赛|
|UR8|SR8.1|系统在开始比赛后开始计时，每回合产生一次信息，通知所有用户|
|UR9|SR9.1|系统可以暂存选手信息|
|   |SR9.2|系统持久化游戏过程以供他用|

### 4. 架构设计

![系统架构图](imgs/总体架构.jpg)

![服务器架构图](imgs/服务端架构.jpg)

