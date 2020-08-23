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
          <ObjectCard title="人员简介" type="person"/>
        </a-col>
        <a-col :md="6" :lg="10">
          <ObjectCard title="业绩展示" type="performance"/>
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
      form: this.$form.createForm(this, { name: 'horizontal_login' }),
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
      });
    },
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
