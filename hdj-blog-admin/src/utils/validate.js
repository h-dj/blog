/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(http[s]?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} email
 * @returns {Boolean}
 */
export function validEmail(email) {
  return /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,8})$/.test(email)
}

/**
 * @param {string} password
 * @returns {Boolean}
 */
export function validPassword(password) {
  return /^([0-9A-Za-z_@&$]{6,8})$/.test(password)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validRoleName(str) {
  return /(\w){3,9}/.test(str)
}
