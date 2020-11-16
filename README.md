# 피플미 기업과제 

> 요구사항 

1. 회원탈퇴 후 재가입 가능 기간 로직 추가하기 
    * 추후에 기간을 두고 재가입할 수 있도록 로직변경이 필요
    * 기간을 두고 재가입 가능 ( 30일)

2. 클라이언트에서 전달받은 latitude 와 longitude를 이용하여 DB 내에 등록된 박물관과의 거리를 계산해 가장 가까운 박물관 순서로 나열하시오.     
    * SQL Query
    * Java SpringBoot 메소드
    * (알고리즘 구현 방법 or 풀이 방법을 함께 포함해서 전달)

```
// 2. 클라이언트에서 전달받은 latitude 와 longitude를 이용하여
// DB 내에 등록된 박물관과의 거리를 계산해 가장 가까운 박물관 순서로 나열하시오.
//
// DB 에 latitude와 longitude가 등록돼있습니다.
// (큐피커 내 기능 구현.)
//
//  2-1 SQL Query
//  2-2 Java SpringBoot 메소드
//
//
//
// 알고리즘 구현 방법 또는 풀이 방법을 함께 주시면 좋을거 같습니다.

// 알고리즘은 음... 현재 위치를 기준으로 거리를 도출한다. (x - x1), (y - y1) 이렇게 두 x,y 좌표 차이 절대값, 구해서 제곱한 값이 거리라고 볼 수 있음!
// Comparable 메서드를 구현해서 sort를 그렇게 해도 될 것 같음!
``` 
