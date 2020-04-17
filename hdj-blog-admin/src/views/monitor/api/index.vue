<template>
  <Frame :src="swaggerApi" />
</template>
<script>
import { mapGetters } from 'vuex'
import Frame from '@/components/IFrame/index'
export default {
  name: 'Sql',
  components: { Frame },
  computed: {
    ...mapGetters([
      'swaggerApi'
    ])
  }
}
</script>
