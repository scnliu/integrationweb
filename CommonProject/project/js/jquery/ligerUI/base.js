/**
* jQuery ligerUI 1.1.3
* 
* Author leoxie [ gd_star@163.com ] 
* 
*/
(function ($)
{


    //lgerui �̳з���
    Function.prototype.ligerExtend = function (parent, overrides)
    {
        if (typeof parent != 'function') return this;
        //����Ը��������
        this.base = parent.prototype;
        this.base.constructor = parent;
        //�̳�
        var f = function () { };
        f.prototype = parent.prototype;
        this.prototype = new f();
        this.prototype.constructor = this;
        //�������Է���
        if (overrides) $.extend(this.prototype, overrides);
    };

    // ���Ķ���
    $.ligerui = {
        version: 'V1.1.3',
        managerCount: 0,
        //�����������
        managers: {},
        managerIdPrev: 'ligerui',
        //������ʾ
        error: {
            managerIsExist: '������id�Ѿ�����'
        },
        getId: function (prev)
        {
            prev = prev || this.managerIdPrev;
            var id = prev + (1000 + this.managerCount);
            this.managerCount++;
            return id;
        },
        add : function (manager)
        {
            if (arguments.length == 2)
            {
                var m = arguments[1];
                m.id = m.id || m.options.id || arguments[0].id;
                this.addManager(m);
                return;
            }
            if (!manager.id) manager.id = this.getId(manager.__idPrev());
            if (this.managers[manager.id])
                throw new Error(this.error.managerIsExist);
            this.managers[manager.id] = manager;
        },
        get : function (arg)
        {
            if (typeof arg == "string" || typeof arg == "number")
            {
                return $.ligerui.managers[arg];
            }
            else if (typeof arg == "object" && arg.length)
            {
                return $.ligerui.managers[arg[0].ligeruiid];
            }
            return null;
        },
        //��չ
        //1,Ĭ�ϲ���     {}
        //2,���ػ���չ 
        defaults: {},
        //3,�����ӿ���չ
        methods: {},
        //�����ռ�
        //���Ŀؼ�,��װ��һЩ���÷���
        core: {},
        //�����ռ�
        //����ļ���
        controls: {}
    }; 
    //��չ����
    $.ligerDefaults = {};

    //��չ����
    $.ligerMethos = {};

    //��������
    $.ligerui.defaults = $.ligerDefaults;
    $.ligerui.methods = $.ligerMethos;


    //�������
    //1,��ɶ�������������Ͳ������Գ�ʼ���Ĺ���
    //2,��ɶ����¼����������¼����Գ�ʼ���Ĺ���
    $.ligerui.core.Component = function (options)
    {
        //�¼�����
        this.events = this.events || {};
        //���ò���
        this.options = options || {};
    };
    $.extend($.ligerui.core.Component.prototype, {
        __getType: function ()
        {
            return '$.ligerui.core.Component';
        },
        __idPrev: function ()
        {
            return 'ligerui';
        },
        //��������
        set: function (arg, value)
        {
            if (!arg) return;
            if (typeof arg == 'object')
            {
                var tmp
                if (this.options != arg)
                {
                    $.extend(this.options, arg);
                    tmp = arg;
                }
                else
                {
                    tmp = $.extend({}, arg);
                }
                for (var p in tmp)
                {
                    this.set(p, tmp[p]);
                }
                return;
            }
            var name = arg;
            //�¼�����
            if (name.indexOf('on') == 0)
            {
                if (typeof value == 'function')
                    this.bind(name.substr(2), value);
                return;
            }
            this.trigger('propertychange', arg, value);
            this.options[name] = value;
            var pn = '_set' + name.substr(0, 1).toUpperCase() + name.substr(1);
            if (this[pn])
            {
                this[pn].call(this, value);
            }
            this.trigger('propertychanged', arg, value);
        },
        //��ȡ����
        get: function (name)
        {
            var pn = '_get' + name.substr(0, 1).toUpperCase() + name.substr(1);
            if (this[pn])
            {
                return this[pn].call(this, name);
            }
            return this.options[name];
        },
        hasBind: function (arg)
        {
            var name = arg.toLowerCase();
            var event = this.events[name];
            if (event && event.length) return true;
            return false;
        },
        //�����¼�
        //data (��ѡ) Array(��ѡ)���ݸ��¼��������ĸ��Ӳ���
        trigger: function (arg, data)
        {
            var name = arg.toLowerCase();
            var event = this.events[name];
            if (!event) return;
            data = data || [];
            if ((data instanceof Array) == false)
            {
                data = [data];
            }
            for (var i = 0; i < event.length; i++)
            {
                var ev = event[i];
                if (ev.handler.apply(ev.context, data) == false)
                    return false;
            }
        },
        //���¼�
        bind: function (arg, handler, context)
        {
            if (typeof arg == 'object')
            {
                for (var p in arg)
                {
                    this.bind(p, arg[p]);
                }
                return;
            }
            if (typeof handler != 'function') return false;
            var name = arg.toLowerCase();
            var event = this.events[name] || [];
            context = context || this;
            event.push({ handler: handler, context: context });
            this.events[name] = event;
        },
        //ȡ����
        unbind: function (arg, handler)
        {
            if (!arg)
            {
                this.events = {};
                return;
            }
            var name = arg.toLowerCase();
            var event = this.events[name];
            if (!event && !event.length) return;
            if (!handler)
            {
                delete this.events[name];
            }
            else
            {
                for (var i = 0, l = event.length; i < l; i++)
                {
                    if (event[i].handler == handler)
                    {
                        event.splice(i, 1);
                        break;
                    }
                }
            }
        },
        destroy: function () { }
    });


    //�����������, 
    //1,��ɽ����ʼ��:�������id�����������������,��ʼ������
    //2,��Ⱦ�Ĺ���,ϸ�ڽ�������ʵ��
    //@parm [element]   �����Ӧ��dom element����
    //@parm [options] ����Ĳ���
    $.ligerui.core.UIComponent = function (element, options)
    {
        $.ligerui.core.UIComponent.base.constructor.call(this, options);
        var extendMethods = this._extendMethods();
        if (extendMethods) $.extend(this, extendMethods);
        this.element = element;
        this._init();
        this.trigger('render');
        this._render();
        this.trigger('rendered');
        this._rendered();
    };
    $.ligerui.core.UIComponent.ligerExtend($.ligerui.core.Component, {
        __getType: function ()
        {
            return '$.ligerui.core.UIComponent';
        },
        //��չ����
        _extendMethods: function ()
        {

        },
        _init: function ()
        {
            this.type = this.__getType();
            if (!this.element)
            {
                this.id = this.options.id || $.ligerui.getId(this.__idPrev());
            }
            else
            {
                this.id = this.options.id || this.element.id || $.ligerui.getId(this.__idPrev());
            }
            //�����������
            $.ligerui.add(this);

            if (!this.element) return;

            //��ȡattr����,�����ص�����,����['url']
            var attributes = this.attr();
            if (attributes && attributes instanceof Array)
            {
                for (var i = 0; i < attributes.length; i++)
                {
                    var name = attributes[i];
                    this.options[name] = $(this.element).attr(name);
                }
            }
            //��ȡligerui������ԣ������ص����������� ligerui = "width:120,heigth:100"
            var p = this.options;
            if ($(this.element).attr("ligerui"))
            {
                try
                {
                    var attroptions = $(this.element).attr("ligerui");
                    if (attroptions.indexOf('{') != 0) attroptions = "{" + attroptions + "}";
                    eval("attroptions = " + attroptions + ";");
                    if (attroptions) $.extend(p, attroptions);
                }
                catch (e) { }
            }
        },
        _render: function ()
        {

        },
        _rendered: function ()
        {
            if (this.element)
            {
                this.element.applyligerui = true;
                this.element.ligeruiid = this.id;
            }
        },
        //����Ҫת����ligerui����������,����['url']
        attr: function ()
        {
            return [];
        }
    });


    //���ؼ�����
    $.ligerui.controls.Input = function (element, options)
    {
        $.ligerui.controls.Input.base.constructor.call(this, element, options);
    };

    $.ligerui.controls.Input.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return '$.ligerui.core.Input';
        },
        setValue: function (value)
        {
            this.set('value', value);
        },
        getValue: function ()
        {
            return this.get('value');
        },
        setEnabled: function ()
        {
            this.set('disabled', false);
        },
        setDisabled: function ()
        {
            this.set('disabled', true);
        },
        updateStyle: function ()
        {

        }
    });


})(jQuery);