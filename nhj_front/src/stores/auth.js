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
      if(localStorage.getItem('token')) {
        const profileRes = await local.get('/member/profile')
        this.user = profileRes.data

        //이미지 처리
        if(this.user.img) {
          this.user.img = "http://localhost" + this.user.img
        }

        console.log(this.user)

        //댓글 알림(개수) 처리
        const response = await local.get('/companion-board/comment/notice', {params: {'userId': this.user.id}})
        this.user.notification = response.data.comments.length

        this.isLoggedIn = true
      }
    },
    async login(user) {
      const loginRes = await local.post(`/member/login`, user)
      localStorage.setItem('token', loginRes.headers.authorization)

      const profileRes = await local.get('/member/profile')
      this.user = profileRes.data

      //이미지 처리
      this.user.img = "http://localhost" + this.user.img

      console.log(this.user)

      this.isLoggedIn = true
    },
    logout() {
      localStorage.removeItem('token');
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
