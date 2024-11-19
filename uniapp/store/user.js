export default{
	namespaced: true,
	
	state: () => ({
		token: JSON.parse(uni.getStorageSync('token') || '0'),
		user: JSON.parse(uni.getStorageSync('user') || '{}')
	}),
	
	mutations: {
		loginToken(state, user){
			state.token = '1'
			state.user = user
			this.commit('m_user/saveTokenToStorage')
		},
		logoutToken(state){
			state.token = ''
			this.commit('m_user/saveTokenToStorage')
		},
		saveTokenToStorage(state){
			uni.setStorageSync('token', JSON.stringify(state.token))
			uni.setStorageSync('user', JSON.stringify(state.user))
		},
		changeUsername(state, username){
			state.user.username = username
			this.commit('m_user/saveTokenToStorage')
		},
		changeUserSex(state, sex){
			state.user.sex = sex
			this.commit('m_user/saveTokenToStorage')
		},
		changeIntroduction(state, introduction){
			state.user.introduction = introduction
			this.commit('m_user/saveTokenToStorage')
		}
	},
	
	getters: {}
}