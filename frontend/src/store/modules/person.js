export default {
  namespaced: true,
  state: {
    person: {},
    message: '',
    validNameReg: /^([a-z]+|[а-яё]+)$/i,
    validEmailReg: /^[^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*\@[-a-z]+\.[a-z]{2,}$/i,
    validPasswordReg: /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).{8,}$/
  },
  getters: {
    getFullName: state => {
      return state.person.name + ' ' + state.person.surname;
    },
    getRegBtnDisabled: state => {
      if (!state.person.name || !state.person.surname || !state.person.email || !state.person.password) {
        return true;
      } else {
        if (!state.validNameReg.test(state.person.name) || !state.validNameReg.test(state.person.surname) 
          || !state.validEmailReg.test(state.person.email) || !state.validPasswordReg.test(state.person.password)) {
          return true;
        }
      }
      return false;
    },
    getAuthBtnDisabled: state => {
      if (!state.person.email || !state.person.password) {
        return true;
      } else {
        if (!state.validEmailReg.test(state.person.email)) {
          return true;
        }
      }
      return false;
    }
  },
  mutations: {
    delPerson: (state) => {
      state.person = {}
    },
    setPerson: (state, data) => {
      state.person.email = data.email;
      state.person.password = data.password;
    },
    setMessage: (state, data) => {
      if (data.id) {
        state.person.id = data.id;
        state.person.name = data.name;
        state.person.surname = data.surname;
        state.person.token = data.token;
        state.message = data.message;
      } else {
        state.message = data.message;
      } 
    }
  },
  actions: {
    sendRegInfoToServer({commit}, data) {
      fetch(`http://localhost:8080/user/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(data)
      })
        .then(response => response.json())
        .then(responseJson => commit('setMessage', responseJson))

    },
    sendAuthInfoToServer({commit}, data) {
      fetch(`http://localhost:8080/user/auth`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(data)
      })
        .then(response => response.json())
        .then(responseJson => commit('setMessage', responseJson))
    }
  }
}
