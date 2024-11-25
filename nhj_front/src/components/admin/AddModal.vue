<template>
    <div class="modal-container">
      <div class="modal-overlay" @click="closeModal"></div>
      <div class="modal-content">
        <div class="modal-header">
          <h2>Create {{ tableName }}</h2>
        </div>
      <form class="modal-form" @submit.prevent="handleSave">
        <div
          v-for="(column, i) in columns"
          :key="column"
          class="form-group"
        >
          <!-- 라벨과 NULL/타입 정보 -->
          <div class="label-wrapper">
            <label :for="column" class="modal-label">
              {{ column }}:
            </label>
            <div class="column-info-wrapper">
              <span
                v-if="columnInfo[i]?.IS_NULLABLE === 'NO'"
                class="column-warning"
              >
                (NOT NULL)
              </span>
              <small class="column-info">
                {{ columnInfo[i]?.COLUMN_TYPE }}
              </small>
            </div>
          </div>
  
          <!-- 입력 필드 -->
          <input
            :id="column"
            v-model="formData[column]"
            :placeholder="columnInfo[i]?.COLUMN_COMMENT || ''"
            :type="columnInfo[i]?.COLUMN_TYPE?.includes('varchar') ? 'text' : columnInfo[i]?.COLUMN_TYPE || 'text'"
            class="modal-input"
            :readonly="columnInfo[i]?.COLUMN_KEY === 'PRI' && columnInfo[i]?.EXTRA?.includes('auto_increment')"
          />
        </div>
  
        <!-- 모달 푸터 버튼 -->
        <div class="modal-footer">
          <button
            type="button"
            class="modal-btn cancel-btn"
            @click="closeModal"
          >
            Cancel
          </button>
          <button
            type="submit"
            class="modal-btn save-btn"
          >
            Save
          </button>
        </div>
      </form>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'AddModal',
    props: {
      rowData: {
        type: Object,
        required: true,
      },
      columns: {
        type: Array,
        required: true,
      },
      columnInfo: {
        type: Array,
        required: true,
      },
      tableName: {
        type: String,
        required: true,
      },
    },
    data() {
      return {
        formData: { ...this.rowData }, // 기존 데이터 복사
      };
    },
    methods: {
      closeModal() {
        this.$emit('close');
      },
      handleSave() {
        this.$emit('save', this.formData);
      },
    },
  };
  </script>
  
  <style scoped>
  .modal-container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 50;
  }
  
  .modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    z-index: 40;
  }
  .modal-content {
    position: relative;
    width: 100%;
    max-width: 600px;
    background-color: #222;
    padding: 2rem;
    z-index: 60;
    border-radius: 4px;
  }
  .modal-header {
    margin-bottom: 1.5rem;
  }
  
  .modal-header h2 {
    color: #22c55e;
    font-size: 1.5rem;
  }
  
  .modal-form {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    max-height: 80vh; /* 높이 제한 */
    overflow-y: auto; /* 스크롤 활성화 */
    z-index: 50;
  }
  
  .form-group {
    display: flex;
    align-items: flex-start; /* 상단 정렬 */
    justify-content: space-between;
    gap: 1rem;
  }
  
  .label-wrapper {
    flex: 1; /* 라벨 넓이 조정 */
  }
  
  .modal-label {
    font-size: 1.25rem;
    color: #ffffff;
    font-weight: 500;
  }
  
  .column-info-wrapper {
    margin-top: 0.25rem; /* 위로 여백 */
    display:flex;
    gap:10px;
  }
  
  .column-warning {
    font-size: 0.7rem;
    color: #ef4444; /* 빨간색 */
    font-weight: bold;
  }
  
  .column-info {
    display: block; /* 라벨 아래로 표시 */
    font-size: 0.75rem;
    color: #9ca3af; /* 연한 회색 */
  }
  
  .modal-input {
    flex: 2; /* 입력 필드 넓이 조정 */
    padding: 0.75rem;
    background-color: transparent;
    border: 1px solid #4b5563;
    border-radius: 4px;
    color: white;
    outline: none;
    width: 100%;
  }
  
  .modal-input:focus {
    border-color: #22c55e;
  }
  
  .modal-input::placeholder {
    color: #9ca3af;
  }
  
  .modal-footer {
    display: flex;
    justify-content: flex-end;
    gap: 1rem;
  }
  
  .modal-btn {
    padding: 0.5rem 1.5rem;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .cancel-btn {
    background-color: #6b7280;
    color: white;
  }
  
  .save-btn {
    background-color: #22c55e;
    color: white;
  }
  
  .modal-form::-webkit-scrollbar {
    width: 8px;
  }
  
  .modal-form::-webkit-scrollbar-thumb {
    background: #4b5563;
    border-radius: 4px;
  }
  </style>