/** @type {import('tailwindcss').Config} */
export default {
  prefix: 'tw-', // 모든 Tailwind 클래스 앞에 'tw-'를 추가
  content: [
    "./index.html",      // index.html 파일
    "./src/**/*.{vue,js,ts,jsx,tsx}" // Vue 컴포넌트 파일들
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}

