<template>
  <view>
    <!-- Search box for input -->
    <view class="search-box">
      <uni-search-bar 
        @input="input" 
        @confirm="onConfirm" 
        :radius="100" 
        cancelButton="none"
      />
    </view> 

    <!-- Search results display -->
    <view class="sugg-list" v-if="searchResults.length !== 0">
      <view 
        class="sugg-item" 
        v-for="(item, i) in searchResults" 
        :key="i" 
        @click="gotoSuchTalk(item.id)"
      >
        <view class="goods-name">{{ item.content }}</view>
        <uni-icons type="arrowright" size="16" />
      </view> 
    </view>

    <!-- History box when no search results -->
    <view class="history-box" v-else>
      <view class="history-title"> 
        <text>搜索历史</text>
        <uni-icons 
          type="trash" 
          size="17" 
          @click="clean"
        />
      </view>

      <view class="history-list">
        <uni-tag 
          class="tag" 
          :text="item" 
          v-for="(item, i) in histories" 
          :key="i" 
          @click="tagSwitch(item)"
        />
      </view> 
    </view>
  </view>
</template>

<script>
  import { $http } from '@escook/request-miniprogram'

  uni.$http = $http
  $http.baseUrl = 'https://database.ccjy16.top'

  // Request interceptor
  $http.beforeRequest = function (options) {
    uni.showLoading({
      title: '数据加载中...',
    })
  }

  // Response interceptor
  $http.afterRequest = function () {
    uni.hideLoading()
  }

  export default {
    data() {
      return {
        timer: null,
        kw: '',
        searchResults: [],
        historyList: [],
      }
    },

    onLoad() {
      this.historyList = JSON.parse(uni.getStorageSync("kw") || "[]")
    },

    methods: {
      // Input event handler
      input(e) {
        this.kw = e
        clearTimeout(this.timer)
        this.timer = setTimeout(() => {
          this.getSearchList(e)
        }, 500)
      },

      // Confirm search
      onConfirm() {
        if (this.kw.length === 0) return

        uni.navigateTo({
          url: '/subpkg/searchList/searchList?data=' + JSON.stringify(this.kw),
        })
      },

      // Get search results
      async getSearchList(e) {
        if (e.length === 0) {
          this.searchResults = []
          return
        }

        const res = await $http.get("/WxChatSearchServlet?index=" + e)
        if (res.statusCode !== 200) {
          return uni.showToast({
            title: '数据请求失败！',
            duration: 1500,
            icon: 'none',
          })
        }
        this.searchResults = res.data
        this.saveSearchHistory(e)
      },

      // Save search history
      saveSearchHistory(e) {
        this.historyList.push(e)
        const set = new Set(this.historyList)
        set.delete(e)
        set.add(e)
        this.historyList = Array.from(set)
        uni.setStorageSync("kw", JSON.stringify(this.historyList))
      },

      // Clear search history
      clean() {
        this.historyList = []
        uni.setStorageSync("kw", "[]")
      },

      // Tag click handler
      tagSwitch(item) {
        uni.navigateTo({
          url: '/subpkg/searchList/searchList?data=' + JSON.stringify(item),
        })
      },

      // Navigate to specific talk
      gotoSuchTalk(id) {
        uni.navigateTo({
          url: '/subpkg/suchtalk/suchtalk?id=' + id,
        })
      },
    },

    computed: {
      // Reverse history list for display
      histories() {
        return [...this.historyList].reverse()
      },
    },
  }
</script>

<style lang="scss">
  .search-box {
    position: sticky;
    top: 0;
    z-index: 999;
  }

  .sugg-list {
    padding: 0 5px;

    .sugg-item {
      font-size: 12px;
      padding: 13px 0;
      border-bottom: 1px solid #efefef;
      display: flex;
      align-items: center;
      justify-content: space-between;

      .goods-name {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        margin-right: 3px;
      }
    }
  }

  .history-box {
    padding: 0 5px;

    .history-title {
      display: flex;
      justify-content: space-between;
      height: 40px;
      align-items: center;
      font-size: 13px;
      border: 1px solid #efefef;
    }

    .history-list {
      display: flex;
      flex-wrap: wrap;

      .tag {
        margin-top: 5px;
        margin-right: 5px;
      }
    }
  }
</style>
