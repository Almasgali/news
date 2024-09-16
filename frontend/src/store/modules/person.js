export default {
  namespaced: true,
  state: {
    person: {
      name: '',
      lastName: '',
      email: '',
      password: ''
    },
    message: '',
    validNameReg: /^([a-z]+|[а-яё]+)$/i,
    validEmailReg: /^[^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*\@[-a-z]+\.[a-z]{2,}$/i,
    validPasswordReg: /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).{8,}$/
  },
  getters: {
    getFullName: state => {
      return state.person.name + ' ' + state.person.lastName;
    }
  },
  mutations: {
    addPerson: (state, data) => {
      state.person.name = data.name;
      state.person.lastName = data.lastName;
      state.person.email = data.email;
      state.person.password = data.password;
    },
    delPerson: (state) => {
      state.person = {
        name: '',
        lastName: '',
        email: '',
        password: ''
      }
    },
    setPerson: (state, data) => {
      state.person.email = data.email;
      state.person.password = data.password;
    },
    setMessage: (state, data) => {
      console.log(data);
      state.message = data.status;
    },
    setUserAndMessage: (state, data) => {
      console.log(data);
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
        .then(response => commit('setMessage', response));
    },
    sendAuthInfoToServer({commit}, data) {
      fetch(`http://localhost:8080/user/auth`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(data)
      })
        .then(response => commit('setUserAndMessage', response));
    }
  }
}
