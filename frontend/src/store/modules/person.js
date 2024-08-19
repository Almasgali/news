export default {
  state: {
    person: {
      name: '',
      lastName: '',
      email: '',
      password: ''
    },
    validName: /^([a-z]+|[а-яё]+)(\s([a-z]+|[а-яё]+)){0,2}$/i
  },
  getters: {
    getName: state => {
      return state.person.name + ' ' + state.person.lastName;
    },
    getValidName: (state) => {
      return state.validName; 
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
    setPeople: (state, data) => {
      state.person.name = data.name;
      state.person.lastName = data.lastName;
    }
  },
  actions: {
    
  }
}
