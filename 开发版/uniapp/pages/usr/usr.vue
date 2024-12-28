<template>
  <view>
    <my-login v-if="!token" />
    <my-userinfo v-else />
  </view>
</template>

<script>
import { mapState } from 'vuex';

export default {
  computed: {
    ...mapState('m_user', ['token']),
  },
  data() {
    return {};
  },
};
</script>

<style lang="scss">
/* Add your styles here */
</style>