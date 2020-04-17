<template>
  <Frame :src="sqlApi" />
</template>
<script>
import { mapGetters } from 'vuex'
import Frame from '@/components/IFrame/index'
export default {
  name: 'Sql',
  components: { Frame },
  computed: {
    ...mapGetters([
      'sqlApi'
    ])
  }
}
</script>
