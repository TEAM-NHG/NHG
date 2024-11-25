<template>
    <aside class="sidebar">
      <nav>
        <ul>
          <li class="menu-item" @click="scrollToSection('dashboard')">대시보드</li>
          <li>
            <div class="menu-item-with-children">
              <div class="menu-item" @click="toggleDataManagement">
                데이터 관리
                <span class="toggle-icon">{{ isDataManagementOpen ? '▼' : '▶' }}</span>
              </div>
              <ul v-if="isDataManagementOpen" class="submenu">
                <li v-for="tableInfo in tableList" 
                    :key="tableInfo.id" 
                    class="submenu-item"
                    @click="selectName(tableInfo)">
                  {{ tableInfo.TABLE_NAME }}
                </li>
              </ul>
            </div>
          </li>
        </ul>
      </nav>
    </aside>
  </template>
  
<script>
    import { ref } from 'vue';
    import { localAxios } from "@/util/http-commons";
    const local = localAxios();
  
  export default {
    name: 'Sidebar',
    data() {
      return {
        isDataManagementOpen: false,
        tableList: [],
        selectedName: null,
        loading: false,
        error: null
      }
    },
    methods: {
      toggleDataManagement() {
        this.isDataManagementOpen = !this.isDataManagementOpen;
        if (this.isDataManagementOpen && this.tableList.length === 0) {
          this.fetchNameList();
        }
      },
      async fetchTableList() {
        try {
          this.loading = true;
          const response = await local.get('/admin/tables');
          this.tableList = response.data;
        } catch (error) {
          this.error = '이름 목록을 불러오는데 실패했습니다.';
          console.error('Error fetching names:', error);
        } finally {
          this.loading = false;
        }
      },
      selectName(tableInfo) {
        this.selectedName = tableInfo.TABLE_NAME;
        console.log(tableInfo)
        this.$emit('name-selected', tableInfo.TABLE_NAME);
        this.scrollToSection('data-management');
      },
      scrollToSection(sectionId) {
        this.$emit('scroll-to-section', sectionId);
      }
    },
    created() {
      // 초기 로딩시 이름 목록 가져오기
      this.fetchTableList();
    }
  }
  </script>
  
  <style scoped>
  .sidebar {
    grid-column: 1 / 2;
    padding: 2rem 1rem;
    border-right: 1px solid #00ff00;
    background-color: #1a1a1a;
  }
  
  .menu-item {
    color: #00ff00;
    list-style: none;
    margin-bottom: 1rem;
    cursor: pointer;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0.5rem;
  }
  
  .menu-item:hover {
    background-color: #2a2a2a;
  }
  
  .menu-item-with-children {
    margin-bottom: 0.5rem;
  }
  
  .submenu {
    list-style: none;
    padding-left: 1.5rem;
    margin-top: 0.5rem;
  }
  
  .submenu-item {
    color: #00ff00;
    padding: 0.5rem;
    cursor: pointer;
    font-size: 0.9em;
  }
  
  .submenu-item:hover {
    background-color: #2a2a2a;
  }
  
  .toggle-icon {
    font-size: 0.8em;
  }
  
  /* 로딩 상태 표시 */
  .loading {
    color: #888;
    padding: 0.5rem;
    text-align: center;
  }
  
  /* 에러 메시지 */
  .error {
    color: #ff4444;
    padding: 0.5rem;
    font-size: 0.8em;
  }
  </style>