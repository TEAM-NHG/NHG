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
    checkLoginStatus() {
      // 세션 스토리지에서 'user' 정보 확인
      const user = sessionStorage.getItem('user');
      this.isLoggedIn = !!user; // user가 존재하면 true, 없으면 false
      if(user) {
        this.user = JSON.parse(user)
      }
    },
    login(user) {
      // 로그인 처리: 세션 스토리지에 저장하고 상태 업데이트
      sessionStorage.setItem('user', user);
      this.isLoggedIn = true;
      this.user = JSON.parse(user)
    },
    logout() {
      // 로그아웃 처리: 세션 스토리지에서 제거하고 상태 업데이트
      sessionStorage.removeItem('user');
      this.isLoggedIn = false;
      this.user = null
    },
    updateProfile(user) {
      try {
        local.post('/member/modify', user)
        sessionStorage.setItem('user',JSON.stringify(user))
        this.user = JSON.parse(sessionStorage.getItem('user'))
      }catch{
        console.log('error 발생')
      }
    }
  },
});
