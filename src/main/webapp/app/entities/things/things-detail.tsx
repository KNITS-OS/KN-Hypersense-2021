import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './things.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IThingsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ThingsDetail = (props: IThingsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { thingsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="thingsDetailsHeading">Things</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{thingsEntity.id}</dd>
          <dt>
            <span id="thingUuid">Thing Uuid</span>
          </dt>
          <dd>{thingsEntity.thingUuid}</dd>
          <dt>Production Line</dt>
          <dd>{thingsEntity.productionLine ? thingsEntity.productionLine.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/things" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/things/${thingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ things }: IRootState) => ({
  thingsEntity: things.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ThingsDetail);
