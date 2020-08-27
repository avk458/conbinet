<template>
  <div>
    <a-card :title="title">
      <a-button slot="extra" type="link" @click="handleAdd">
        新增
      </a-button>
      <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="res.data">
        <a-list-item slot="renderItem" key="item.name" slot-scope="item">
          <template slot="actions">
            <a-button size="small" type="primary" icon="edit" @click="handleEdit(item)" style="margin-left: 10px"/>
            <a-button size="small" type="danger" icon="delete" @click="handleDelete(item)"/>
          </template>
          <img
              slot="extra"
              width="168"
              alt="logo"
              :src="item.detailImageUrl"
          />
          <a-list-item-meta :description="item.description">
            <a slot="title" :href="item.href">{{ item.name }}</a>
            <a-avatar shape="square" slot="avatar" :size="128" :src="item.coverImageUrl" />
          </a-list-item-meta>
        </a-list-item>
      </a-list>
    </a-card>
    <AddModal ref="addModal" @success="refresh"/>
  </div>
</template>

<script>
import AddModal from '@/components/AddModal'

export default {
  name: 'ObjectCard',
  props: {
    title: {
      required: true,
      type: String
    },
    type: {
      required: true,
      type: String
    },
    res: {
      required: true,
      type: Object
    }
  },
  components: {
    AddModal
  },
  data() {
    return {
      pagination: {
        onChange: page => {
          this.pagination.current = page
          this.refresh()
        },
        current: this.res.page,
        pageSize: 3,
        total: this.res.size,
        size: 'small'
      }
    }
  },
  methods: {
    handleAdd() {
      this.$refs.addModal.showModal(this.type, undefined)
    },
    refresh() {
      this.$emit('fetch-data', this.pagination)
    },
    handleEdit(item) {
      this.$refs.addModal.showModal(this.type, {...item})
    },
    handleDelete(item) {
      const that = this
      this.$confirm({
        title: `您确定要删除${item.name}的信息吗？`,
        onOk() {
          const url = item._links.self.href
          that.$axios.delete(url).then(res => {
            if (204 === res.status) {
              that.$message.success('删除成功');
              that.refresh();
            } else {
              that.$message.error('删除失败')
            }
          })
        }
      })
    }
  },
  watch: {
    res(n) {
      this.pagination.total = n.size
    }
  }
}
</script>

<style scoped>

</style>
