# 피플미 기업과제 

## 요구사항 

1. 회원탈퇴 후 재가입 가능 기간 로직 추가하기 
    * 추후에 기간을 두고 재가입할 수 있도록 로직변경이 필요
    * 기간을 두고 재가입 가능 ( 30일)

2. 클라이언트에서 전달받은 latitude 와 longitude를 이용하여 DB 내에 등록된 박물관과의 거리를 계산해 가장 가까운 박물관 순서로 나열하시오.     
    * SQL Query
    * Java SpringBoot 메소드
    * (알고리즘 구현 방법 or 풀이 방법을 함께 포함해서 전달)
    
## 사고과정 및 풀이과정 
### 1. 재가입 가능 기간 로직 추가하기 
```
(기존 탈퇴 기능은 수정할 필요가 없다고 판단하여 제외하였습니다.) 
회원 탈퇴를 하면, userName, password, thirdPartUserId, email 정보를 비우고(=null), 
leaved 를 true로 변경하고 탈퇴시각(=leavedAt)을 현재 시간으로 업데이트하고있습니다. 

주어진 조건에 따라서 회원 탈퇴 후, 30일 동안은 재가입을 방지하고 30일 이후에 회원 가입을 가능하게 설정해야 한다고 생각했습니다. 
날짜와 시간 API인 LocaldateTime의 기능을 통해서 '회원 탈퇴 이후 30일이 지났는지' 판단할 수 있었습니다. 
(탈퇴일로부터 30일 이후의 날짜를 구하고 현재 날짜가 앞의 날짜 이후인지 확인하여 진위여부를 파악했습니다) 
 
회원가입 기능 UserApiController[join]을 통해서 UserService[join] 메소드가 실행됩니다. 
Controller단을 통해서 회원가입 정보(UserSaveRequestDto)를 받아오고 Service단의 join 메소드로 전달하게 됩니다. 
(Service단의)join 메소드가 처음 실행되면 validateUserName()을 통해서 userName이 유효한지를 판단하고, 
이 유효성을 판단하는 메소드는 userName이 DB의 User table에 이미 존재하는 경우에 2가지 유효성을 검증합니다. 
(1). isLeaved가 false인 경우는 중복되는 유저가 존재하는 것이므로 예외를 던진다. 
(2) isLeaved가 true인 경우 탈퇴일 이후 30일이 지났는지 확인하여 지나지 않았다면 예외를 던진다. 

조금 우려스러운 부분은 회원 탈퇴시에 남아있는 User 정보를 다시 업데이트하는 방식으로 회원가입을 
진행하는게 좋을까 고민이 됐으나, 서비스단의 로직이 복잡해질 것이라 판단되었고 탈퇴이력을 굳이 삭제할 필요도 없다고 생각이 들었습니다. 
탈퇴이력이 누적됨에따라 또 생긴 고민거리는 userName의 유효성을 검증하는 로직에서 findAllByUserNameOrderByLeavedAtDesc를 통해 
userName으로 탈퇴한(leaved == true) User 정보들을 검색하는 것이었는데, 이 값 중에서 가장 최근 탈퇴이력을 가지고 와야했습니다. 
조건상(leaved == true) leavedAt이 null인 경우가 없기 떄문에 의도한대로 탈퇴날짜의 내림차순으로 정렬하는데 
오류가 발생하지는 않겠지만, 이는 예상치못한 오류를 발생시킬 수도 있다고 판단되는데 이를 개선할 방법이 떠오르지 않아 수정하지 못했습니다.  
```

### 2. DB 내에 등록된 박물관과의 거리를 계산해 가장 가까운 박물관 순서로 나열하는 기능 

```
사용자의 위치에따라 박물관과의 거리는 달라질 것이기 때문에, 사용자의 위치를 파라미터로 받아오고, 
모든 DB내의 박물관과의 거리를 계산해서 거리차가 적은 순서대로 List를 나열하여 클라이언트에게 전달한다면, 
원하는대로 기능을 구현할 수 있다고 생각했습니다. 

저는 Database가 아닌 서비스단에서 거리를 계산하여 정렬하는 기능을 구현하였습니다. 
stream을 통해서 sort() 하였고, 람다식으로 Comparator를 구현하여 거리의 오름차순으로 
정렬하는 로직을 작성하였습니다. 사용자의 위치에 따라서 거리가 달라지기 때문에 Service단에서 
구현해야한다고 생각했습니다. 먼저 MuseumApiController[findAllMuseums]를 통해서 현재 사용자의 
위치좌표(=Position) (latitude, longitude)를 받아오고, 이 값을 기준으로 모든 DB내의 박물관과 거리를 계산하고 
그 값이 작은 순서대로 오름차순 정렬합니다. 

(1) SQL Query: JPA Hibernate에서 기본적으로 제공하는 findAll 기능을 사용 
Native Query: select * from Museum; 으로 추정  
(2) Java SpringBoot 메소드: Service단의 'findAllMuseumOrderByDistance()' 기능, Controller단의 
```
