export default {
  namespaced: true,
  state: {
    person: {
      name: '',
      lastName: '',
      email: '',
      password: ''
    },
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
    }
  },
  actions: {
    
  }
}
