module.exports = {
    "env": {
        "es2021": true,
        "node": true,
        "jest": true
    },
    "extends": "standard-with-typescript",
    "overrides": [
        {
            "env": {
                "node": true
            },
            "files": [
                ".eslintrc.{js,cjs}"
            ],
            "parserOptions": {
                "sourceType": "script"
            }
        }
    ],
    "parserOptions": {
        "ecmaVersion": "latest",
        "sourceType": "module"
    },
    "rules": {
        "@typescript-eslint/space-before-function-paren": "off",
        "@typescript-eslint/explicit-function-return-type": "off",
    }
}
