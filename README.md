# strong-keyboard
自定义功能强大的数字、字母键盘

## 数字键盘

### 样式

![加载失败](https://github.com/theshapeofmyheart/strong-keyboard/blob/master/number_keyboard.png)

### 功能

⑴ 可输出正负整数、正负小数  
⑵ 可设置是否支持小数、小数位数限制（仅支持设置保留一位、二位小数）  
⑶ 提供显示要输入属性的名称、范围（Range）  
⑷ 键盘最长接收字符长度为18，输入完成，点击OK，若输入值不在给出的范围内，则给出超出范围（Out of range）提示  

### 使用

⑴ 创建键盘实例  
⑵ 设置键盘显示位置  
⑶ 设置键盘输入完成结果值回调    
⑷ 打开键盘  
   参数说明：min表示属性范围下限；max表示属性范围上限； decNumber表示设置小数位数，设置0位表示只能输入整数，设置1或2分表表示1位和2位小数；code用来绑定属性值，方便在完成输入后结果回调时知道是哪个属性，继而对获取的结果值作相应操作；title表示属性名称

## 字母键盘

![加载失败](https://github.com/theshapeofmyheart/strong-keyboard/blob/master/letter_keyboard.png)

![加载失败](https://github.com/theshapeofmyheart/strong-keyboard/blob/master/letter_psw_keyboard.png)

### 功能

⑴ 可输出由字母、数字、符号组成的文本字符串  
⑵ 提供要输入的属性名称（顶部中间显示），以及最大字符长度  
⑶ 在输入过程中，若字符数已经达到给出的最大字符长度，则编辑框右侧显示提示框超出范围（Out of range）  
⑷ 支持字母大小写切换  
⑸ 支持密文显示  

### 使用

⑴ 创建键盘实例  
⑵ 设置键盘显示位置  
⑶ 设置键盘输入完成结果值回调  
⑷ 打开键盘  
   参数说明：maxLength表示输入属性的最大字符长度；code用来绑定属性值，方便在完成输入后结果回调时知道是哪个属性，继而对获取的结果值作相应操作；title表示属性名称；isPassword表示是否以密文方式显示

