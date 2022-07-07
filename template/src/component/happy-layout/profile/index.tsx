import React, { useEffect, useState } from "react";

import { shallowEqual } from "react-redux";
import { useAppDispatch, useAppSelector } from '@/redux/hook'
import { saveUserAvatar as saveUserAvatrSlice } from '@/redux/slice/user'

import { Drawer, Form, Input, Button, Upload, message, Space } from 'antd';
import { RcFile, UploadChangeParam } from "antd/es/upload/interface";
import { LoadingOutlined, PlusOutlined } from "@ant-design/icons";

import { HappyLayoutProfileContainer } from '@/component/happy-layout/profile/style';
import { uploadUrl } from '@/api/common';
import { saveUserAvatar } from '@/api/users';
import { UserBaseInfo } from "@/redux/types/User";

interface IProps {
  isVisible: boolean
  closeModal: () => void
}

const HappyLayoutProfileModal: React.FC<IProps> = (props: IProps) => {

  const dispath = useAppDispatch();
  const [form] = Form.useForm();

  const user: UserBaseInfo = useAppSelector((state: any) => ({ ...state.user.data }), shallowEqual);

  const [loading, setLoading] = useState<boolean>(false);
  const [imageUrl, setImageUrl] = useState<string>();
  const [avatar, setAvatar] = useState<string>();

  useEffect(() => {
    if (props.isVisible) {
      setImageUrl(user.avatar)
    }
  }, [props.isVisible])// eslint-disable-line react-hooks/exhaustive-deps

  const getBase64 = (img: Blob, callback: (imgUrl: string) => void) => {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result!.toString()));
    reader.readAsDataURL(img);
  }

  const handleChange = (info: UploadChangeParam) => {
    if (info.file.status === 'uploading') {
      setLoading(true);
      return;
    }
    if (info.file.status === 'done') {
      setAvatar(info.file.response.data);
      // Get this url from response in real world.
      getBase64(info.file.originFileObj!, (imageUrl: string) => {
        setImageUrl(imageUrl);
        setLoading(false);
      });
    }
  };

  const beforeUpload = (file: RcFile) => {
    const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
    if (!isJpgOrPng) {
      message.error('You can only upload JPG/PNG file!');
    }
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
      message.error('Image must smaller than 2MB!');
    }
    return isJpgOrPng && isLt2M;
  }

  const uploadButton = (
    <div>
      {loading ? <LoadingOutlined /> : <PlusOutlined />}
      <div style={{ marginTop: 8 }}>上传</div>
    </div>
  )

  const handleSaveAvatar = () => {
    if (avatar !== undefined) {
      saveUserAvatar(avatar).then(res => {
        dispath(saveUserAvatrSlice(imageUrl!))
        message.success('头像更新成功!');
      })
    }
  }

  const onFinish = () => {
    console.log(form.getFieldsValue());

  }

  return (
    <HappyLayoutProfileContainer>
      <Drawer
        title="个人设置"
        placement={'top'}
        visible={props.isVisible}
        onClose={() => props.closeModal()}
        height={480}
        extra={
          <Space>
            <Button onClick={() => props.closeModal()}>关闭</Button>
            <Button onClick={() => form.submit()} type="primary">
              更新
            </Button>
          </Space>
        }
      >
        <Form
          form={form}
          name="user-profile"
          labelCol={{ span: 7 }}
          wrapperCol={{ span: 8 }}
          onFinish={() => onFinish()}
          onFinishFailed={() => { }}
          autoComplete="off"
        >

          <Form.Item
            label="头像"
            extra={'点击直接保存'}
          >
            <Upload
              name="file"
              listType="picture-card"
              className="avatar-uploader"
              showUploadList={false}
              action={uploadUrl()}
              beforeUpload={beforeUpload}
              onChange={handleChange}
            >
              {imageUrl ? <img src={imageUrl} alt="avatar" style={{ width: '100%' }} /> : uploadButton}
            </Upload>

            <Button type="primary" onClick={() => handleSaveAvatar()}>替换头像</Button>
          </Form.Item>

          <Form.Item
            label="旧密码"
            name="old_password"
            extra={'如不修改,可不填'}
          >
            <Input.Password placeholder="旧密码" />
          </Form.Item>

          <Form.Item
            label="新密码"
            name="password"
          >
            <Input.Password placeholder="新密码" />
          </Form.Item>

          <Form.Item
            label="确认密码"
            name="password_confirmation"
          >
            <Input.Password placeholder="确认密码" />
          </Form.Item>

        </Form>
      </Drawer>
    </HappyLayoutProfileContainer>
  );
}

HappyLayoutProfileModal.defaultProps = {
  isVisible: false,
  closeModal: () => { console.log('关闭个人设置') }
}

export default HappyLayoutProfileModal;