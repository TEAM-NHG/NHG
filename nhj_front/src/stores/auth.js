import { defineStore } from 'pinia'
import { localAxios } from "@/util/http-commons";
const local = localAxios();

export const useAuthStore = defineStore('auth', {
  state: () => {
    if (sessionStorage.getItem('user') === null) {
      return {
        isLoggedIn: false, // 로그인 여부 초기화
        user: null,
      };
    }
  },
  actions: {
    async checkLoginStatus() {
      // 세션 스토리지에서 'user' 정보 확인
      const user = sessionStorage.getItem('user');
      this.isLoggedIn = !!user; // user가 존재하면 true, 없으면 false
      if(user) {
        this.user = JSON.parse(user)

        //댓글 알림(개수) 처리
        const response = await local.get('/companion-board/comment/notice', {params: {'userId': this.user.id}})
        this.user.notification = response.data.comments.length
      }
    },
    async login(user) {
      // 로그인 처리: 세션 스토리지에 저장하고 상태 업데이트\
      this.isLoggedIn = true;
      this.user = user

      //이미지 처리
      this.user.img = this.user.img ? "http://localhost" + user.img : ''
      sessionStorage.setItem('user', JSON.stringify(this.user));

      //댓글 알림(개수) 처리
      const response = await local.get('/companion-board/comment/notice', {params: {'userId': this.user.id}})
      this.user.notification = response.data.comments.length
      
    },
    logout() {
      // 로그아웃 처리: 세션 스토리지에서 제거하고 상태 업데이트
      sessionStorage.removeItem('user');
      this.isLoggedIn = false;
      this.user = null
    },
    async updateProfile(user) {
      try {
        await local.post('/member/modify', user, {
          headers: { "Content-Type": "multipart/form-data" },
        });

        const response = await local.get('/member/profile', {
          withCredentials: true, // 쿠키를 포함한 요청을 보냄
        })

        this.user = response.data
        this.user.img = this.user.img ? "http://localhost" + this.user.img : ''
        sessionStorage.setItem('user', JSON.stringify(response.data))
      }catch{
        console.log('error 발생')
      }
    }
  },
});
