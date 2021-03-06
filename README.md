<p align="center"><img src="https://user-images.githubusercontent.com/44575072/127271143-dccef01e-ac3c-446c-8b24-3f3345fb5c19.png"></p>

***코로나, 미세먼지, 날씨 API 정보를 활용해 여행 가기 최적화된 경상도 관광지를 추천해주는 여기로 가 입니다.***
  
<br/>

## 개발환경
- Eclipse
- Oracle
- Github
- Open API


## 사용기술
* Spring boot
  * JAVA 11
  * Maven
  * Spring Data JPA
  
* Database
  * Oracle
  
* FrontEnd
  * JavaScript
  * Thymeleaf


## 팀 소개
- [김영석](https://github.com/yeongseokdev) - 레이아웃
- [박준성](https://github.com/JUNSEONG97) - 게시판 
- [신재영](https://github.com/tsd02150) - DB, 메인페이지
- [최현진](https://github.com/hjchoi0207) - DB, 관광정보 페이지

## 발표자료
[발표자료 다운로드](https://github.com/hjchoi0207/tourism/files/6890820/modulproject.pptx)

<br/>
<br/>
<hr>
<br/>

## STS 환경에서 Github 연동하는 과정입니다.
  - 반드시 개인 브랜치에서 작업 후 merge한다.
  - 브랜치명은 각자의 이름으로한다
  - push전 master branch pull은 필수로 이행한다.
  - 별도의 Pakage 추가 시 즉시 psuh & 알려준다.
  
## 1. Local에 Repository Clone
1. (STS 상단 메뉴) : [Window] > [Show View] > [Other] > [Git] > [Git Repositories] 선택
2. (Git Repositories) : [Clone a Git repository] > 깃허브URL 넣으면 자동완성 > Clone되는 경로기억  
  
## 2. STS에 Repository Clone
1. (왼쪽 Pakage Explorer) : [import] > [General] > [Projects from Folder or Archive]   
 ** import source부분에 경로입력, 아래 Folder 두 개 뜨는데 위에 체크해제 **
2. (STS 상단 메뉴) : [Window] > [Show View] > [Terminal]
3. (하단 Terminal) : open a terminal(모니터모양) 클릭 후 그대로 OK한 뒤 STS에 Clone한 경로로 이동  
  
## 예시 (환경마다 다를 수 있음)

```
$ cd git/tourism_forTest/
chjin@DESKTOP ~/git/tourism (master)
$ git branch
```
  
$git branch 입력시 * master 가 뜬다면 성공, Clone할 때 있던 Branch들은 보이지않으나 checkout으로 이동가능  

## 3. 개인 Branch 생성 및 이용방법
```
$ git branch hyunjin    // 브랜치 생성
$ git checkout hyunjin  // 브랜치 이동
$ git branch            // 현재 브랜치 위치
```
  
## 4. 개인 Branch에서 자유롭게(?) 작업한 후에 push하는 과정
```
$ git add .
$ git commit -m "커밋할 메세지 입력"
$ git pull origin master    // 필수
$ git push --set-upstream origin hyunjin // 브랜치 생성 후 최초로 push한다면 한 번만 입력
$ git push    // 개인 브랜치에 push
```
  
## 5. 마무리로 master branch에 병합
```
$ git pull origin master
$ git checkout master     // 브랜치 이동
$ git merge hyunjin       // hyunjin 브랜치와 병합
$ git push origin master   // or $ git push
```
