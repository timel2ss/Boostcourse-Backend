# ProjectB. 방명록 만들기

https://www.boostcourse.org/web326/project/206/content/165#summary

## Environment
- java 8
- spring boot 2.4.1
- gradle
- jdbcTemplate
- mariaDB
- thymeleaf

## TODO
1. MariaDB에 방명록과 로그 테이블을 생성합니다.
2. 주어진 프로젝트의 library dependencies를 gradle로 import합니다.
3. 방명록 저장, 삭제, 목록 조회가 View 페이지에서 동작되도록 하세요.
4. 방명록 저장, 삭제, 목록 조회의 기능을 API로 제공하세요.

### Code Details
#### 방명록 저장
##### GuestbookController
```java
@PostMapping("guestbook/write")
public String write(GuestbookDto.Create guestbookDto, HttpServletRequest httpServletRequest) {
    Guestbook guestbook = new Guestbook();
    guestbook.setName(guestbookDto.getName());
    guestbook.setContent(guestbookDto.getContent());
    guestbook.setRegdate(new Date());

    String clientIp = httpServletRequest.getHeader("X-FORWARDED-FOR") != null ? httpServletRequest.getHeader("X-FORWARDED-FOR") : httpServletRequest.getRemoteAddr();
    guestbookService.write(guestbook, clientIp);
    return "redirect:list";
}
```
##### GuestbookService
```java
@Transactional(readOnly = false)
public Guestbook write(Guestbook guestbook, String ip) {
    Guestbook writeOne = guestbookRepository.write(guestbook);

    Log log = new Log();
    log.setIp(ip);
    log.setMethod("INSERT");
    log.setRegdate(new Date());
    logRepository.insert(log);
    return writeOne;
}
```

#### 방명록 삭제
##### GuestbookController
```java    
@PostMapping("guestbook/delete")
public String delete(@RequestParam(name = "id") Long id, HttpServletRequest httpServletRequest) {
    String clientIp = httpServletRequest.getHeader("X-FORWARDED-FOR") != null ? httpServletRequest.getHeader("X-FORWARDED-FOR") : httpServletRequest.getRemoteAddr();
    guestbookService.delete(id, clientIp);
    return "redirect:list";
}
```
##### GuestbookService
```java
@Transactional(readOnly = false)
public int delete(long id, String ip) {
    int deleteNum = guestbookRepository.delete(id);

    Log log = new Log();
    log.setIp(ip);
    log.setMethod("DELETE");
    log.setRegdate(new Date());
    logRepository.insert(log);
    return deleteNum;
}
```

#### 방명록 목록 조회
##### GuestbookController
```java
@GetMapping("guestbook/list")
public String list(@RequestParam(name="start", required = false, defaultValue = "0") int start, Model model) {
    int count = guestbookService.getCount(); // 방명록의 전체 개수를 반환
    int pageCount = count / GuestbookService.LIMIT;
    if(count % GuestbookService.LIMIT > 0) {
        pageCount++;
    } // 페이지 수 지정

    List<Integer> pageStartList = new ArrayList<>();
    for(int i = 0; i < pageCount; i++) {
        pageStartList.add(i * GuestbookService.LIMIT);
    } // page의 start지점을 저장

    model.addAttribute("count", count);
    model.addAttribute("pages", pageStartList);
    model.addAttribute("list", guestbookService.getList(start));
    return "list";
}
```
##### GuestbookService
```java
@Transactional
public List<Guestbook> getList(int start) {
    /*
    	List의 start지점에서 LIMIT 길이만큼의 목록을 반환한다.
    	ex) start = 5 / LIMIT = 5 => 5 ~ 9번 방명록이 반환
    */
    return guestbookRepository.getList(start, start + LIMIT);
   
}
```

#### 방명록 API
https://documenter.getpostman.com/view/8107109/TVzUCFvo

### Execution
#### 방명록 조회 목록
(Format) [방명록의 ID]-이름-내용-등록일자
![image](https://user-images.githubusercontent.com/62116902/104473700-9b465a80-5600-11eb-90c3-d8b6e9f26c45.png)
