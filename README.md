# 나혼자간다

혼행인들을 위한 서비스 나혼자간다입니다

# 시연 영상
#### MainPage
###### snapscroll로 웹 사이트를 간단하게 안내합니다.
<img width="" src="https://github.com/user-attachments/assets/bd2edd51-49e4-4763-b0db-d2730c194eb6"/>

#### SignUp
###### 회원가입 창입니다.
###### 각각의 입력창에서 유효성 검사를 실시합니다.
<img width="60%" src="https://github.com/user-attachments/assets/7ceed1d7-8c56-45a9-922f-3f96351ab420"/>

#### ID/Pwd
###### 아이디와 비밀번호 찾기 모달창입니다.
###### 비밀번호는 재설정만 가능합니다.
<img width="60%" src="https://github.com/user-attachments/assets/a8887495-6723-418f-87e7-7fce77a88785"/>

#### Curation
###### 공공데이터포털의 API를 사용해 여행 정보를 카드형태로 제공합니다. 페이징과 필터가 적용됩니다.
###### 특정 카드를 클릭하면 지도와 함께 정보가 모달로 나타나고 이모티콘 클릭시 상세 정보가 나타납니다.
<img width="60%" src="https://github.com/user-attachments/assets/e43f7ae5-5ee4-4f5a-ab02-7fdbf44b3885"/>

#### Review
###### 네이버 블로그 API를 호출하여 리뷰를 무한스크롤로 제공합니다.
###### 검색어를 입력 시 "혼자 여행" 이라는 키워드가 붙게 됩니다.
<img width="60%" src="https://github.com/user-attachments/assets/e166c217-a51d-4dad-afa6-2eb6bb3c4153"/>

#### Board
###### 동행인을 구하거나 여행 후기를 남길 수 있는 게시판입니다.
###### 수정과 삭제는 글을 작성한 사람만 가능합니다.
<img width="60%" src="https://github.com/user-attachments/assets/345fd412-99e1-4857-bcb1-16cb7c43588f"/>

#### ModalButton
###### 여행 정보 모달창에서 각각의 버튼 클릭시 장소명에 맞는 후기 혹은 게시판이 검색됩니다.
<img width="60%" src="https://github.com/user-attachments/assets/8052c45e-1cd4-4858-aa68-2dade8e93f26"/>

#### Comment
###### 댓글과 대댓글을 작성할 수 있습니다.
###### 수정과 삭제는 댓글을 작성한 사람만 가능합니다.
###### 타인의 글에 댓글을 달거나 대댓글을 작성하면 게시자에게 댓글 알림이 생성됩니다.
<img width="60%" src="https://github.com/user-attachments/assets/22091e17-cf14-4523-8ebb-9026a2eaa4f2"/>

#### AI Planner
###### 생성형 AI를 활용해 여행 계획을 만들거나 평가 받을 수 있습니다.
###### 특정 캐릭터마다 출력의 말투가 달라집니다.
###### 오른쪽 아래 구슬을 클릭하여 입출력 텍스트를 저장할 수 있습니다.
<img width="60%" src="https://github.com/user-attachments/assets/26acadef-e7a2-44e6-af80-1a08f429979b"/>

#### My Page
###### 기본정보 수정을 할 수 있습니다.
###### 생성했던 여행 계획을 확인하고 수정, 삭제가 가능합니다.
<img width="60%" src="https://github.com/user-attachments/assets/4a7cb1bc-37f4-45e8-ada2-a938f5a79d2e"/>

#### Notification
###### 자신이 작성한 게시글 혹은 댓글에 달린 대댓글 알림을 확인할 수 있습니다.
###### 모든 알림 확인 시 오른쪽 위 프로필에 빨간 알림이 사라집니다.
<img width="60%" src="https://github.com/user-attachments/assets/06013ab8-3bd8-40f9-9093-e034a5d9f14c"/>

#### Admin Page
###### 관리자 페이지 입니다.
###### 모든 데이터의 생성, 조회, 수정, 삭제가 가능합니다.
<img width="60%" src="https://github.com/user-attachments/assets/d86ad5af-dc81-4ac2-b3b0-49f5acefabbc"/>


## Commit Convention

나혼자간다의 커밋컨벤션입니다.. >o< ~!!

🎨
| **이모지** | **태그**                | **설명**                                   | **영문 설명**                                |
|------------|-------------------------|-------------------------------------------|---------------------------------------------|
| 🎨         | `:art:`                | 코드의 구조/형태 개선                     | Improve structure / format of the code.    |
| ⚡️         | `:zap:`                | 성능 개선                                 | Improve performance.                        |
| 🔥         | `:fire:`               | 코드/파일 삭제                            | Remove code or files.                       |
| 🐛         | `:bug:`                | 버그 수정                                 | Fix a bug.                                  |
| 🚑         | `:ambulance:`          | 긴급 수정                                 | Critical hotfix.                            |
| ✨         | `:sparkles:`           | 새 기능                                   | Introduce new features.                     |
| 📝         | `:memo:`               | 문서 추가/수정                            | Add or update documentation.                |
| 💄         | `:lipstick:`           | UI/스타일 파일 추가/수정                  | Add or update the UI and style files.       |
| 🎉         | `:tada:`               | 프로젝트 시작                             | Begin a project.                            |
| ✅         | `:white_check_mark:`   | 테스트 추가/수정                          | Add or update tests.                        |
| 🔒         | `:lock:`               | 보안 이슈 수정                            | Fix security issues.                        |
| 📌         | `:pushpin:`            | 특정 버전 의존성 고정                     | Pin dependencies to specific versions.      |
| 👷         | `:construction_worker:`| CI 빌드 시스템 추가/수정                  | Add or update CI build system.              |
| ♻️         | `:recycle:`            | 코드 리팩토링                             | Refactor code.                              |
| ➕         | `:heavy_plus_sign:`    | 의존성 추가                               | Add a dependency.                           |
| ➖         | `:heavy_minus_sign:`   | 의존성 제거                               | Remove a dependency.                        |
| 💩         | `:poop:`               | 똥싼 코드                                 | Write bad code that needs to be improved.   |
| 📦         | `:package:`            | 컴파일된 파일 추가/수정                   | Add or update compiled files or packages.   |
| 👽         | `:alien:`              | 외부 API 변화로 인한 수정                 | Update code due to external API changes.    |
| 🚚         | `:truck:`              | 리소스 이동, 이름 변경                    | Move or rename resources (e.g., files paths routes). |
| 📄         | `:page_facing_up:`     | 라이센스 추가/수정                        | Add or update license.                      |
| 💡         | `:bulb:`               | 주석 추가/수정                            | Add or update comments in source code.      |
| 🗃         | `:card_file_box:`      | 데이터베이스 관련 수정                    | Perform database related changes.           |
| 🔊         | `:loud_sound:`         | 로그 추가/수정                            | Add or update logs.                         |
| 🙈         | `:see_no_evil:`        | .gitignore 추가/수정                      | Add or update a .gitignore file.            |
