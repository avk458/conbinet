<template>
  <div id="app">
    <div v-if="!isLogin" class="login-div">
      <a-form layout="horizontal" :form="form" @submit="handleSubmit">
        <a-form-item :validate-status="userNameError() ? 'error' : ''" :help="userNameError() || ''">
          <a-input
              v-decorator="[
          'username',
          { rules: [{ required: true, message: 'Please input your username!' }] },
        ]"
              placeholder="Username"
          >
            <a-icon slot="prefix" type="user" style="color:rgba(0,0,0,.25)" />
          </a-input>
        </a-form-item>
        <a-form-item :validate-status="passwordError() ? 'error' : ''" :help="passwordError() || ''">
          <a-input
              v-decorator="[
          'password',
          { rules: [{ required: true, message: 'Please input your Password!' }] },
        ]"
              type="password"
              placeholder="Password"
          >
            <a-icon slot="prefix" type="lock" style="color:rgba(0,0,0,.25)" />
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" html-type="submit" :disabled="hasErrors(form.getFieldsError())">
            Log in
          </a-button>
        </a-form-item>
      </a-form>
    </div>
    <div v-else>
      <a-row :gutter="8">
        <a-col :span="2"/>
        <a-col :md="6" :lg="10">
          <ObjectCard ref="person" title="人员简介" type="person" :res="persons" @fetch-data="getPersons"/>
        </a-col>
        <a-col :md="6" :lg="10">
          <ObjectCard title="业绩展示" type="performance" :res="performances" @fetch-data="getPerformances"/>
        </a-col>
        <a-col :span="2"/>
      </a-row>
    </div>
  </div>
</template>

<script>
import ObjectCard from './components/ObjectCard.vue'
function hasErrors(fieldsError) {
  return Object.keys(fieldsError).some(field => fieldsError[field]);
}
export default {
  name: 'App',
  components: {
    ObjectCard
  },
  data() {
    return {
      isLogin: false,
      hasErrors,
      form: this.$form.createForm(this, { name: 'login' }),
      persons: {},
      performances: {}
    }
  },
  methods: {
    // Only show error after a field is touched.
    userNameError() {
      const { getFieldError, isFieldTouched } = this.form;
      return isFieldTouched('userName') && getFieldError('userName');
    },
    // Only show error after a field is touched.
    passwordError() {
      const { getFieldError, isFieldTouched } = this.form;
      return isFieldTouched('password') && getFieldError('password');
    },
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          const { username, password } = values
          if ('admin' === username && '999999' === password) {
            this.isLogin = true
          }
        }
      })
    },
    getPersons(params) {
      let page = 0, size = 3
      if (params) {
        page = params.current - 1;
        size = params.pageSize
      }
      this.$axios(`/person?page=${page}&size=${size}&sort=sequence`).then(res => {
        if (200 === res.status) {
          this.persons = {}
          this.persons.data = res.data._embedded.person;
          this.persons.page = res.data.page.number + 1
          this.persons.size = res.data.page.totalElements
        }
      })
    },
    getPerformances(params) {
      let page = 0, size = 3
      if (params) {
        page = params.current - 1;
        size = params.pageSize
      }
      this.$axios(`/performance?page=${page}&size=${size}&sort=sequence`).then(res => {
        if (200 === res.status) {
          this.performances = {}
          this.performances.data = res.data._embedded.perform
          this.performances.page = res.data.page.number + 1
          this.performances.size = res.data.page.totalElements
        }
      })
    }
  },
  mounted() {
    this.getPersons()
    this.getPerformances()
  }
}
</script>

<style>
#app{
  margin: 100px 0 0 0;
}
.login-div{
  margin: auto;
  width: 400px;
}
</style>
