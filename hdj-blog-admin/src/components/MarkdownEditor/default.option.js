const defaultOptions = {
  toolbar: [{
    hotkey: '⌘-e',
    name: 'emoji',
    tipPosition: 'ne'
  }, {
    hotkey: '⌘-h',
    name: 'headings',
    tipPosition: 'ne'
  }, {
    hotkey: '⌘-b',
    name: 'bold',
    prefix: '**',
    suffix: '**',
    tipPosition: 'ne'
  }, {
    hotkey: '⌘-i',
    name: 'italic',
    prefix: '*',
    suffix: '*',
    tipPosition: 'ne'
  }, {
    hotkey: '⌘-t',
    name: 'strike',
    prefix: '~~',
    suffix: '~~',
    tipPosition: 'ne'
  }, {
    hotkey: '⌘-k',
    name: 'link',
    prefix: '[',
    suffix: '](https://)',
    tipPosition: 'n'
  }, {
    name: '|'
  }, {
    hotkey: '⌘-l',
    name: 'list',
    prefix: '* ',
    tipPosition: 'n'
  }, {
    hotkey: '⌘-o',
    name: 'ordered-list',
    prefix: '1. ',
    tipPosition: 'n'
  }, {
    hotkey: '⌘-j',
    name: 'check',
    prefix: '* [ ] ',
    tipPosition: 'n'
  }, {
    name: '|'
  }, {
    hotkey: '⌘-.',
    name: 'quote',
    prefix: '> ',
    tipPosition: 'n'
  }, {
    hotkey: '⌘-⇧-d',
    name: 'line',
    prefix: '---',
    tipPosition: 'n'
  }, {
    hotkey: '⌘-u',
    name: 'code',
    prefix: '```\n',
    suffix: '\n```',
    tipPosition: 'n'
  }, {
    hotkey: '⌘-g',
    name: 'inline-code',
    prefix: '`',
    suffix: '`',
    tipPosition: 'n'
  }, {
    name: 'upload',
    tipPosition: 'n',
    tip: '上传图片'
  }, {
    name: 'record',
    tipPosition: 'n'
  }, {
    hotkey: '⌘-m',
    name: 'table',
    prefix: '| col1',
    suffix: ' | col2 | col3 |\n| --- | --- | --- |\n|  |  |  |\n|  |  |  |',
    tipPosition: 'n'
  }, {
    name: '|'
  }, {
    hotkey: '⌘-z',
    name: 'undo',
    tipPosition: 'n'
  }, {
    hotkey: '⌘-y',
    name: 'redo',
    tipPosition: 'n'
  }, {
    name: '|'
  }, {
    hotkey: '⌘-⇧-m',
    name: 'wysiwyg',
    tipPosition: 'nw'
  }, {
    hotkey: '⌘-p',
    name: 'both',
    tipPosition: 'nw'
  }, {
    hotkey: '⌘-⇧-p',
    name: 'preview',
    tipPosition: 'nw'
  }, {
    hotkey: '⌘-⇧-f',
    name: 'format',
    tipPosition: 'nw'
  }, {
    name: '|'
  }, {
    hotkey: "⌘-'",
    name: 'fullscreen',
    tipPosition: 'nw'
  }, {
    name: 'info',
    tipPosition: 'nw'
  }, {
    name: 'help',
    tipPosition: 'nw'
  }],
  typewriterMode: false,
  tab: '\t',
  cache: true,
  height: 'auto',
  lang: 'zh_CN',
  placeholder: '输入',
  mode: 'markdown-show',
  preview: {
    delay: 500,
    show: false
  },
  upload: {
    max: 5 * 1024 * 1024,
    accept: ['image/gif', 'image/png', 'image/jpeg', 'image/jpg']
  }
}

export default defaultOptions
