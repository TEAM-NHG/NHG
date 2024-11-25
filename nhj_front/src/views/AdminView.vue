<template>
    <div class="admin-container">
        <!-- Header -->
        <header class="header">
            <h1 class="title">나혼자 간다</h1>
            <div class="user-info">
                <span>admin님 환영합니다.</span>
                <div class="header-buttons">
                    <a href="http://localhost/swagger-ui/index.html" target="_blank" class="header-button">
                        swagger
                    </a>
                    <a href="/" class="header-button">
                        웹사이트
                    </a>
                </div>
            </div>
        </header>
  
        <!-- Sidebar -->
        <Sidebar 
            @scroll-to-section="scrollToSection"
            @name-selected="handleNameSelected"
        />
  
      <!-- Main Content -->
      <main class="main-content">
        <!-- Dashboard Section -->
        <section id="dashboard" ref="dashboard" class="dashboard-section">
    <div class="dashboard-header">
        <h2 class="section-title">대시보드</h2>
        <!-- API 상태 체크 -->
        <div class="api-status-card">
            <h5>API 상태</h5>
            <div class="api-status">
                <div class="api-item">
                    <span>Naver API:</span>
                    <span :class="['status-indicator', naverApiStatus]">
                        {{ naverApiStatus === "active" ? '정상' : '오류' }}
                    </span>
                </div>
                <div class="api-item">
                    <span>Tour API:</span>
                    <span :class="['status-indicator', tourApiStatus]">
                        {{ tourApiStatus === "active" ? '정상' : '오류' }}
                    </span>
                </div>
            </div>
        </div>
    </div>

    <!-- 하단 그리드 -->
    <div class="dashboard-grid">
        <div class="table-info-card">
            <h3>테이블 정보</h3>
            <div class="info-content">
                <table class="table-info-table">
                    <thead>
                        <tr>
                            <th>테이블 이름</th>
                            <th>총 레코드</th>
                            <th>테이블 설명</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="info in tableInfos" :key="info.name">
                            <td>{{ info.TABLE_NAME }}</td>
                            <td>{{ info.TABLE_ROWS }}</td>
                            <td>{{ info.TABLE_COMMENT }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="user-graph-card">
            <h3>유저 관련 그래프</h3>
            <p>유저 관련 그래프가 들어갈 예정</p>
        </div>
    </div>
</section>
  
        <!-- Data Management Section -->
        <section id="data-management" ref="dataManagement" class="member-section">
            <h2 class="section-title">{{ selectedName }}</h2>
            <p class="section-subtitle">{{ selectedName }} 테이블입니다.</p>
            <!-- Loading and Error States -->
            <div v-if="loading" class="loading-state">
                데이터를 불러오는 중...
            </div>
            <div v-if="error" class="error-state">
                {{ error }}
            </div>
            <!-- Table Content -->
            <div v-if="!loading && !error">
                <!-- Search and Filter Section -->
                <div class="search-bar">
                    <div class="filter-actions">
                        <!-- 필터 입력 폼 -->
                        <div class="filter-form">
                            <select v-model="pendingFilter.column" class="filter-column">
                                <option value="">컬럼 선택</option>
                                <option v-for="column in tableColumns" :key="column" :value="column">
                                    {{ column }}
                                </option>
                            </select>
                            <input 
                                type="text" 
                                v-model="pendingFilter.value" 
                                placeholder="값 입력" 
                                class="filter-value"
                            />
                            <button @click="addFilter" class="add-filter-button">필터 추가</button>
                            
                        </div>
                        <button @click="showAddModal" class="add-record-button">
                                데이터 추가
                        </button>
                    </div>

                    <!-- Filter List -->
                    <div class="filters">
                        <div v-for="(filter, index) in filters" :key="index" class="filter-item">
                            <span>{{ filter.column }}: {{ filter.value }}</span>
                            <button @click="removeFilter(index)" class="remove-filter-button">제거</button>
                        </div>
                    </div>
                </div>

                <!-- Dynamic Table -->
                <div class="data-table-container">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th v-for="column in tableColumns" :key="column">
                                    {{ column }}
                                    <button @click="sortTable(column)" class="sort-button">
                                        {{ getSortIcon(column) }}
                                    </button>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(row, index) in tableData" :key="index">
                                <td 
                                    v-for="column in tableColumns" 
                                    :key="column"
                                    @click="column === tableColumns[0] ? showEditModal(row) : null"
                                    :class="{ 'clickable-id': column === tableColumns[0] }"
                                >
                                    {{ row[column] }}
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- 페이지네이션 컴포넌트 -->
            <VPageNavigation
                v-if="tableData.length > 0"
                :current-page="currentPage"
                :total-page="totalPage"
                @pageChange="onPageChange"
                style="margin: 30px 0;"
            />
        </section>
    </main>
    <!-- Modals -->
    <EditModal
        v-if="showEditModalFlag"
        :row-data="selectedRow"
        :columns="tableColumns"
        :columnInfo="columnInfo"
        :table-name="selectedName"
        @close="closeEditModal"
        @save="handleSave"
    />
    
    <AddModal
        v-if="showAddModalFlag"
        :table-name="selectedName"
        :columnInfo="columnInfo"
        :columns="tableColumns"
        :row-data="selectedRow"
        @close="closeAddModal"
        @save="handleAdd"
    />

    </div>
</template>
  
<script>
import Sidebar from '@/components/admin/Sidebar.vue';
import VPageNavigation from '@/components/common/VPageNavigation.vue';
import EditModal from '@/components/admin/EditModal.vue';
import AddModal from '@/components/admin/AddModal.vue';
import { localAxios } from "@/util/http-commons";

const local = localAxios();

export default {
    name: 'AdminPage',
    
    components: {
        Sidebar,
        VPageNavigation,
        EditModal,
        AddModal,
    },
    
    data() {
        return {
            selectedName: null,
            tableData: [],
            tableColumns: [],
            columnInfo: [],
            loading: false,
            error: null,
            filters: [],
            sortConfig: {
                column: null,
                direction: 0,
            },
            currentPage: 1,
            totalPage: 0,
            showEditModalFlag: false,
            showAddModalFlag: false,
            selectedRow: null,
            pendingFilter: { column: '', value: '' },
            // 대시보드 관련 데이터
            tableInfos: [],
            naverApiStatus: 'active',
            tourApiStatus: 'active',
            currentSection: 'dashboard'
        }
    },
  
    methods: {
        // API 상태 체크
        async checkApiStatus() {
            try {
                const [naverResponse, tourResponse] = await Promise.all([
                    local.get('/trip-info/search/blog'),
                    local.get('/trip-info/search'),
                ]);
                console.log(naverResponse);
                if (naverResponse.status === 200) {
                    this.naverApiStatus = "active";
                } else {
                    this.naverApiStatus = "inactive";
                }
                if (tourResponse.status === 200) {
                    this.tourApiStatus = "active";
                } else {
                    this.tourApiStatus = "inactive";
                }
            } catch (error) {
                console.error('API 상태 체크 실패:', error);
            }
        },

        // 테이블 정보 가져오기
        async fetchTableInfos() {
            try {
                const response = await local.get('/admin/tables');
                this.tableInfos = response.data;
            } catch (error) {
                console.error('테이블 정보 조회 실패:', error);
            }
        },

        // 모달 관련 메서드
        showAddModal() {
            this.showAddModalFlag = true;
        },

        closeAddModal() {
            this.showAddModalFlag = false;
        },

        
        showEditModal(row) {
            this.selectedRow = { ...row };
            this.showEditModalFlag = true;
        },
        
        closeEditModal() {
            this.showEditModalFlag = false;
            this.selectedRow = null;
        },
        
        // 필터 관련 메서드
        addFilter() {
            if (this.pendingFilter.column && this.pendingFilter.value) {
                this.filters.push({ ...this.pendingFilter });
                this.pendingFilter = { column: '', value: '' };
                this.fetchTableDataPost();
            }
        },
        
        removeFilter(index) {
            this.filters.splice(index, 1);
            this.fetchTableDataPost();
        },
        
        // 섹션 스크롤 처리
        scrollToSection(sectionId) {
            this.currentSection = sectionId;
            const element = this.$refs[sectionId];
            if (element) {
                element.scrollIntoView({ 
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        },
        
        // 테이블 데이터 관련 메서드
        async fetchTableDataPost(page = 1) {
            this.loading = true;
            this.error = null;
            
            try {
                const requestData = {
                    tableName: this.selectedName,
                    page: page,
                    filters: this.filters,
                    sortConfig: this.sortConfig
                };
                
                const response = await local.post('/admin/tables', requestData);
                const columnInfoResponse = await local.get(`/admin/tables/${this.selectedName}/columns`);
                const rows = response.data.table;
                if (rows.length > 0) {
                    this.tableColumns = Object.keys(rows[0]);
                }
                this.columnInfo = columnInfoResponse.data;
                this.tableData = rows;
                this.currentPage = response.data.currentPage;
                this.totalPage = response.data.lastPage;
                
            } catch (error) {
                console.error('Error fetching table data:', error);
                this.error = '테이블 데이터를 불러오는데 실패했습니다.';
            } finally {
                this.loading = false;
            }
        },
        
        async handleNameSelected(tableName) {
            this.resetState();
            this.selectedName = tableName;
            await this.fetchTableDataPost();
            this.scrollToSection('data-management');
        },
        
        resetState() {
            this.filters = [];
            this.pendingFilter = { column: '', value: '' };
            this.sortConfig = {
                column: null,
                direction: 0
            };
            this.currentPage = 1;
        },
        
        onPageChange(page) {
            this.currentPage = page;
            this.fetchTableDataPost(page);
        },
        async handleAdd(newData) {
            try {
                // 필수 값 검증
                const missingFields = this.columnInfo
                .filter(info => info.IS_NULLABLE === "NO" && !newData[info.COLUMN_NAME] && info.EXTRA != "auto_increment")
                .map(info => info.COLUMN_NAME);

                if (missingFields.length) {
                throw new Error(`Missing required fields: ${missingFields.join(", ")}`);
                }
                
                //유횽하지않은값검증
                const invalidFields = this.columnInfo
                    .filter(info => info.COLUMN_TYPE.includes("int") && isNaN(newData[info.COLUMN_NAME]) && newData[info.COLUMN_NAME] != null)
                    .map(info => info.COLUMN_NAME);
                console.log(invalidFields)
                if (invalidFields.length) {
                    throw new Error(`Invalid data type for fields: ${invalidFields.join(", ")}`);
                }
                //최대길이검증
                const lengthExceedFields = this.columnInfo.filter(info => {
                    const maxLengthMatch = info.COLUMN_TYPE.match(/varchar\((\d+)\)/);
                    return (
                    maxLengthMatch &&
                    newData[info.COLUMN_NAME]?.length > parseInt(maxLengthMatch[1], 10)
                    );
                })
                .map(info => info.COLUMN_NAME);
                if (lengthExceedFields.length) {
                    throw new Error(`Invalid data type for fields: ${invalidFields.join(", ")}`);
                }
                // 요청
                const requestData = { ...newData };
                await local.post(`/admin/tables/${this.selectedName}`, requestData);

                this.closeAddModal();
                this.fetchTableDataPost(this.currentPage);
            } catch (error) {
                console.error('Error adding new record:', error);
            }
        },
        
        async handleSave(updatedData) {
            try {
                // 필수 값 검증
                const missingFields = this.columnInfo
                .filter(info => info.IS_NULLABLE === "NO" && !updatedData[info.COLUMN_NAME] && info.EXTRA != "auto_increment")
                .map(info => info.COLUMN_NAME);

                if (missingFields.length) {
                throw new Error(`Missing required fields: ${missingFields.join(", ")}`);
                }
                
                //유횽하지않은값검증
                const invalidFields = this.columnInfo
                    .filter(info => info.COLUMN_TYPE.includes("int") && isNaN(updatedData[info.COLUMN_NAME]) && updatedData[info.COLUMN_NAME] != null)
                    .map(info => info.COLUMN_NAME);
                console.log(invalidFields)
                if (invalidFields.length) {
                    throw new Error(`Invalid data type for fields: ${invalidFields.join(", ")}`);
                }
                //최대길이검증
                const lengthExceedFields = this.columnInfo.filter(info => {
                    const maxLengthMatch = info.COLUMN_TYPE.match(/varchar\((\d+)\)/);
                    return (
                    maxLengthMatch &&
                    updatedData[info.COLUMN_NAME]?.length > parseInt(maxLengthMatch[1], 10)
                    );
                })
                .map(info => info.COLUMN_NAME);
                if (lengthExceedFields.length) {
                    throw new Error(`Invalid data type for fields: ${invalidFields.join(", ")}`);
                }
                // 요청
                const requestData = { ...updatedData };
                const response = await local.put(
                    `/admin/tables/${this.selectedName}`,
                    requestData
                );
                
                this.fetchTableDataPost(this.currentPage);
                this.closeEditModal();
            } catch (error) {
                console.error('Error updating row:', error);
            }
        },

        sortTable(column) {
            if (this.sortConfig.column === column) {
                this.sortConfig.direction = (this.sortConfig.direction + 1) % 3;
            } else {
                this.sortConfig.column = column;
                this.sortConfig.direction = 1;
            }
            this.fetchTableDataPost();
        },

        getSortIcon(column) {
            if (this.sortConfig.column !== column || this.sortConfig.direction === 0) {
                return "↕️";
            }
            return this.sortConfig.direction === 1 ? "↑" : "↓";
        },
    },

    async created() {
        await this.handleNameSelected("members");
        await this.fetchTableInfos();
        //await this.checkApiStatus();

        // API 상태 주기적 체크 (5분마다)
        // if (!this.apiCheckInterval) {
        //     this.apiCheckInterval = setInterval(this.checkApiStatus, 3000000);
        // }
    },
    beforeDestroy() {
        // 컴포넌트가 파괴될 때 인터벌 정리
        if (this.apiCheckInterval) {
            clearInterval(this.apiCheckInterval);
            this.apiCheckInterval = null;
        }
    },
}
</script>
  
<style scoped>
/* 기존 스타일 유지 */
.dashboard-section {
    padding: 2rem;
}
.dashboard-header {
    display: flex;
    justify-content: space-between;
    align-items: top;
    margin-bottom: 50px;
}

.dashboard-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 0.5fr));
    gap: 2rem;
    margin-top: 1rem;
}

.api-status-card, .table-info-card {
    background-color: #2a2a2a;
    border: 1px solid #00ff00;
    padding: 0.5rem;
    border-radius: 4px;
}

.api-status-card {
    width: 20rem;
    padding:0.5rem 2rem;
    margin-right:50px;
}



.table-info-card {
    width: 500px;
}

.table-info-table {
    border-spacing: 20px; /* 셀 간 간격 */
}

.table-info-table td, .table-info-table th {
    padding: 5px 20px; /* 셀 내부 간격 */
}

@media (max-width: 768px) {
    .table-info-table {
        border-spacing: 10px; /* 작은 화면에서 간격 줄이기 */
    }
    .table-info-table td, .table-info-table th {
        padding: 10px; /* 작은 화면에서 패딩 줄이기 */
    }
}
.api-status {
    margin-top: 1rem;
}

.api-item {
    display: flex;
    justify-content: space-between;
    margin-bottom: 5px;
}

.status-indicator {
    padding: 0.25rem 0.75rem;
    border-radius: 4px;
    font-size: 0.875rem;
}

.status-indicator.active {
    background-color: rgba(0, 255, 0, 0.2);
    color: #00ff00;
}

.status-indicator.inactive {
    background-color: rgba(255, 0, 0, 0.2);
    color: #ff0000;
}

.info-content {
    margin-top: 1rem;
    margin-right:1rem;
}

.info-content p {
    margin: 0.5rem 1rem;
}

.add-record-button {
    align-items: center;
    padding:0.5rem 1.5rem;
    background-color: #2a2a2a;
    color:#00fbff;
    border-radius: 5px;
    border: 3px solid #00fbff;
}
  @font-face {
    font-family: 'DungGeunMo';
    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/DungGeunMo.woff') format('woff');
    font-weight: normal;
    font-style: normal;
  }
  .navbar {
    display:none;
  }
  .admin-container {
    position: relative;
    font-family: 'DungGeunMo', sans-serif;
    background-color: #1a1a1a;
    color: #00ff00;
    min-height: 100vh;
    display: grid;
    position: fixed; /* 화면 상단에 고정 */
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    
    grid-template-columns: 200px 1fr;
    grid-template-rows: auto 1fr;
  }
  
  .header {
    grid-column: 1 / -1;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1rem 2rem;
    border-bottom: 1px solid #00ff00;
    }

    .title {
        color: #00ff00;
        margin: 0;
        font-size: 1.5rem;
    }

    .user-info {
        display: flex;
        align-items: center;
        gap: 2rem;
    }

    .header-buttons {
        display: flex;
        gap: 1rem;
    }

    .header-button {
        display: flex;
        align-items: center;
        gap: 0.5rem;
        color: #00ff00;
        text-decoration: none;
        padding: 0.5rem 1rem;
        transition: all 0.3s ease;
        background: none;
        border: none;
        cursor: pointer;
        font-family: 'DungGeunMo';
    }

    .header-button:hover {
        background-color: rgba(0, 255, 0, 0.1);
    }

    .button-icon {
        width: 18px;
        height: 18px;
    }

    /* Remove underline from links */
    a {
        text-decoration: none;
    }
  
  .site-button {
    background: none;
    border: 1px solid #00ff00;
    color: #00ff00;
    padding: 0.5rem 1rem;
    cursor: pointer;
    font-family: 'DungGeunMo';
  }
  
  .sidebar {
    grid-column: 1 / 2;
    padding: 2rem 1rem;
    border-right: 1px solid #00ff00;
  }
  
  .menu-item {
    color: #00ff00;
    list-style: none;
    margin-bottom: 1rem;
    cursor: pointer;
  }
  
  .main-content {
    grid-column: 2 / -1;
    padding: 2rem;
  }
  
  .section-title {
    color: #00ff00;
    margin-bottom: 0.5rem;
  }
  
  .section-subtitle {
    color: #00ff00;
    margin-bottom: 2rem;
  }
  
  .search-bar {
    justify-content: space-between;
    margin-bottom: 2rem;
  }
  
  .search-input {
    display: flex;
    align-items: center;
    gap: 0.5rem;
  }
  
  .filter-actions {
    display: flex;
    justify-content: space-between;
  }
  
  .filter-select,
  input[type="text"] {
    background: #2a2a2a;
    border: 1px solid #00fbff;
    color: white;
    padding: 0.5rem;
    font-family: 'DungGeunMo';
  }
  
  .search-button {
    background: none;
    border: 1px solid #00ff00;
    color: #00ff00;
    padding: 0.5rem 1rem;
    cursor: pointer;
    font-family: 'DungGeunMo';
  }
  .data-table-container {
    width: 100%;
    overflow-x: auto; /* 가로 스크롤을 가능하게 설정 */
    -webkit-overflow-scrolling: touch; /* 터치스크린에서 부드러운 스크롤을 위해 추가 */
    }
    .data-table {
    width: 100%;
    border-collapse: collapse;
    color: white;
    border: 3px solid rgba(229, 101, 255, 1);
    table-layout: auto; /* 셀의 크기가 내용에 따라 유동적으로 결정되도록 설정 */
    min-width: 600px; /* 최소 테이블 너비 설정 (필요에 따라 조정) */
}

.data-table th,
.data-table td {
    border: 1px solid rgba(229, 101, 255, 1);
    padding: 0.5rem;
    text-align: left;
    word-wrap: break-word; /* 긴 단어가 줄 바꿈되도록 설정 */
    white-space: nowrap; /* 텍스트가 한 줄로 표시되도록 설정 */
    max-width:  250px; /* 최대 너비 설정 (필요에 따라 조정) */
    overflow: hidden;
    text-overflow: ellipsis;
}

.data-table th {
    font-size:medium;
    letter-spacing: 2px;
    background-color: #212121;
}

.data-table td {
    letter-spacing: 1.5px;
    background-color: #3e3e3e;
}
  
  .summary-section {
    position: absolute;
    top: 2rem;
    right: 2rem;
    border: 1px solid #00ff00;
    padding: 1rem;
    width: 200px;
  }
  /* 기존 스타일은 유지하고 스크롤 관련 스타일 추가 */
  .main-content {
    grid-column: 2 / -1;
    padding: 2rem;
    height: calc(100vh - 64px); /* 헤더 높이를 뺀 만큼 */
    overflow-y: auto; /* 스크롤 가능하도록 설정 */
    scroll-behavior: smooth; /* 부드러운 스크롤 효과 */
    position: relative;
  }
  
  section {
    padding: 2rem 0;
  }
  .filter-form {
    display: flex;
    gap: 1rem; /* 각 항목 사이에 여백 추가 */
    align-items: center; /* 수직 중앙 정렬 */
}
  .filter-item {
    background: #2a2a2a;
    border: 5px solid #00fbff;
    border-radius: 5px;
    padding: 0.5rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 0.5rem;
}
.filters {
    display: flex;
    flex-wrap: wrap; /* 여러 줄로 표시되도록 */
    gap: 1rem; /* 항목들 간의 간격 */
    margin-top: 1rem;
}


.filter-column, .filter-value {
    background: #2a2a2a;
    border: 1px solid #00fbff;
    color: #00fbff;
    border-radius: 5px;
    padding: 0.5rem;
    font-family: 'DungGeunMo';
    height: 2.5rem; /* 높이를 맞춰줌 */
    line-height: 1.5rem;
    width: auto; /* 너비 자동 조정 */
}

.remove-filter-button {
    background: none;
    color: #ff0000;
    border: none;
    cursor: pointer;
    border-radius: 5px;
}

.sort-button {
  background: none;
  border: none;
  color: #00ff00;
  cursor: pointer;
  padding: 0 4px;
  font-family: 'DungGeunMo';
}

.add-filter-button {
    background: #2a2a2a;
    color: #00fbff;
    padding: 0.5rem 1rem;
    border: 1px solid #00fbff;
    border-radius: 5px;
    font-family: 'DungGeunMo';
    cursor: pointer;
}
.clickable-id {
    cursor: pointer;
    color: #00ff00 !important;
    text-decoration: underline;
}

.clickable-id:hover {
    background-color: #2a2a2a;
}

  </style>
